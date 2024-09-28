variable "az" {
  description = "Availability Zone"
  type        = string
  default     = "us-east-1a"
}

variable "key_pair_name" {
  description = "teste"
  type        = string
  default     = "key-0f0600fb44a24f436"
}

variable "ami" {
  description = "AMI ID"
  type        = string
  default     = "ami-0ebfd941bbafe70c6"  # O ID da AMI

}


variable "inst_type" {
  description = "Instance Type"
  type        = string
  default     = "t2.micro"
}

variable "subnet_id" {
  description = "Subnet ID"
  type        = string
  default     = "subnet-05cb7255de055cffc"
}

variable "sg_id" {
  description = "Security Group ID"
  type        = string
  default     = "sg-0a18685ca51d68ffd"
}

variable "dockerhub_username" {
  description = "Docker Hub username"
  type        = string
}

variable "snapshot_id" {
  default = "snap-0d20b4350b63ed2ee"
  description = "Snapshot ID Backend"
  type        = string
}