terraform {
 required_version=">= 1.5.0"
 required_providers { aws={source="hashicorp/aws",version="~> 5.0"} }
}
provider "aws" { region=var.region }
variable "region" { type=string; default="us-east-1" }
variable "instance_type" { type=string; default="t3.small" }
resource "aws_security_group" "gateway" {
 name="buno-aegis-gateway"
 ingress { from_port=8101; to_port=8101; protocol="tcp"; cidr_blocks=[var.allowed_cidr] }
 egress { from_port=0; to_port=0; protocol="-1"; cidr_blocks=["0.0.0.0/0"] }
}
variable "allowed_cidr" { type=string; description="CIDR autorizado, preferencialmente seu IP /32" }
# Exemplo de SG e parâmetros; completar AMI, IAM, rede, registry e deploy seguro antes de apply.
output "security_group_id" { value=aws_security_group.gateway.id }
