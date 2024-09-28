provider "aws" {
  region = "us-east-1"  # Altere para sua região desejada
}

# Criar a instância EC2 chamada private-ec2-01
resource "aws_instance" "public_ec2_backend-1" {
  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = "shh_key"
  subnet_id                   = var.subnet_id
  associate_public_ip_address = false
  vpc_security_group_ids      = [var.sg_id]
  tags = {
    Name = "private-ec2-01"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash
    exec > /var/log/user_data.log 2>&1
    set -x

    export DOCKERHUB_USERNAME=${var.dockerhub_username}

    # Atualizar pacotes e instalar Java
    sudo apt-get update
    sudo apt-get install -y default-jdk

    # Instalar Docker
    sudo apt-get install -y docker.io

    # Instalar docker-compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    docker-compose --version

    # Iniciar e habilitar Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Executar comandos Docker
    sudo docker pull $DOCKERHUB_USERNAME/calencare-api
    sudo docker-compose -f /home/ubuntu/AWS/docker-compose.yml up -d
    EOF
  )
}

# Criar a AMI a partir da instância private-ec2-01
resource "aws_ami_from_instance" "ami_from_private_ec2_01" {
  name                     = "AMI-from-private-ec2-01-${timestamp()}"
  source_instance_id      = aws_instance.public_ec2_backend-1.id
  snapshot_without_reboot  = true

  tags = {
    Name = "AMI-from-private-ec2-01"
  }
}

# (Opcional) Exibir informações sobre a AMI criada
output "ami_id" {
  value = aws_ami_from_instance.ami_from_private_ec2_01.id
}

# Associação de EIP para a instância (opcional)
resource "aws_eip_association" "eip_assoc_01" {
  instance_id  = aws_instance.public_ec2_backend-1.id  # Substitua pelo ID da sua instância
  allocation_id = "eipalloc-04c103f2c5910a4cb"          # ID de alocação do EIP
}
