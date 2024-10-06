resource "aws_instance" "public_ec2_backend-1" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = "teste"
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-01"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Clonar ou atualizar o repositório
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
    else
      cd /home/ubuntu/aws
      sudo git pull origin main  # Atualiza o repositório
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t nhyira-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build
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
  key_name                    = "teste"
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-02"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Clonar ou atualizar o repositório
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
    else
      cd /home/ubuntu/aws
      sudo git pull origin main  # Atualiza o repositório
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t nhyira-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build
    EOF
  )
}

resource "aws_eip_association" "eip_assoc_01" {
  instance_id   = aws_instance.public_ec2_backend-1.id
  allocation_id  = "eipalloc-0f2aa5d16ab6481d7" # ID de alocação do EIP
}