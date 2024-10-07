# Definição do Security Group
resource "aws_security_group" "allow_http_https_mysql_docker" {
  name        = "allow-http-https-mysql-docker"
  description = "Allow HTTP, HTTPS, MySQL, and Docker access"
  vpc_id      = var.vpc_id  # Certifique-se de definir a variável vpc_id

  // Permitir HTTP (porta 80)
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Permitir de qualquer lugar (ajuste conforme necessário)
  }

  // Permitir HTTPS (porta 443)
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  // Permitir MySQL (porta 3306)
  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Ajuste conforme necessário para segurança
  }

  // Permitir acesso ao Docker (geralmente usa portas 2375 e 2376)
  ingress {
    from_port   = 2375
    to_port     = 2375
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 2376
    to_port     = 2376
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  // Permitir SSH (opcional, para acesso remoto)
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Ajuste conforme necessário
  }

  // Permitir todo o tráfego de saída
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"  # -1 significa todos os protocolos
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Definição das Instâncias EC2
resource "aws_instance" "public_ec2_backend-1" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = var.key_pair_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false  # Certifique-se de que está como true
  vpc_security_group_ids      = [aws_security_group.allow_http_https_mysql_docker.id]  # Usar o novo SG
  tags = {
    Name = "private-ec2-01"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Verifica se o repositório já foi clonado
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      echo "Repositório clonado com sucesso"
    else
      echo "Repositório já existe, atualizando..."
      cd /home/ubuntu/aws && sudo git pull
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t calencare-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build -d  # Rodar em segundo plano

    echo "Setup finalizado com sucesso"
    EOF
  )
}

resource "aws_instance" "public_ec2_backend-2" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = var.key_pair_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = true  # Certifique-se de que está como true
  vpc_security_group_ids      = [aws_security_group.allow_http_https_mysql_docker.id]  # Usar o novo SG
  tags = {
    Name = "private-ec2-02"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Verifica se o repositório já foi clonado
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      echo "Repositório clonado com sucesso"
    else
      echo "Repositório já existe, atualizando..."
      cd /home/ubuntu/aws && sudo git pull
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t calencare-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build -d  # Rodar em segundo plano

    echo "Setup finalizado com sucesso"
    EOF
  )
}

resource "aws_eip_association" "eip_assoc_01" {
  instance_id   = aws_instance.public_ec2_backend-1.id
  allocation_id  = "eipalloc-0f2aa5d16ab6481d7" # ID de alocação do EIP
}
