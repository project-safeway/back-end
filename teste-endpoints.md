# Teste dos Endpoints de Autenticação

## Registro de Usuário
**Endpoint**: POST http://localhost:8080/auth/register

**Body**:
```json
{
    "nome": "Usuario Teste",
    "email": "usuario@teste.com",
    "senha": "senha123",
    "tel1": "11999999999"
}
```

## Login
**Endpoint**: POST http://localhost:8080/auth/login

**Body**:
```json
{
    "email": "usuario@teste.com",
    "senha": "senha123"
}
```

## Como testar no PowerShell

### Registro
```powershell
$headers = @{ 
    "Content-Type" = "application/json" 
}

$body = @{
    nome = "Usuario Teste"
    email = "usuario@teste.com"
    senha = "senha123"
    tel1 = "11999999999"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/auth/register" -Method Post -Headers $headers -Body $body
```

### Login
```powershell
$headers = @{ 
    "Content-Type" = "application/json" 
}

$body = @{
    email = "usuario@teste.com"
    senha = "senha123"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/auth/login" -Method Post -Headers $headers -Body $body
```

## Como testar usando cURL no Windows
### Registro
```bash
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d "{\"nome\":\"Usuario Teste\",\"email\":\"usuario@teste.com\",\"senha\":\"senha123\",\"tel1\":\"11999999999\"}"
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d "{\"email\":\"usuario@teste.com\",\"senha\":\"senha123\"}"
```

## Como testar usando Postman/Insomnia
1. Crie uma nova requisição POST
2. Configure o URL (http://localhost:8080/auth/register ou http://localhost:8080/auth/login)
3. Adicione o header "Content-Type: application/json"
4. No body, selecione "JSON" e cole o corpo da requisição correspondente
5. Envie a requisição
