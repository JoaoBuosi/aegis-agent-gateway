# 01 — Buno Aegis Agent Gateway
Gateway REST para chamadas de agentes/personas ao Buno Aegis 2 Runtime. Inclui sessões em memória, endpoint de health e configuração do adaptador.
```bash
docker compose up --build
```
`POST http://localhost:8101/api/agents/run`
```json
{"sessionId":"demo-1","persona":"mentor-socratico","prompt":"Explique injeção de dependência em Java"}
```
`GET /api/agents/sessions/demo-1` lista histórico da sessão. Histórico é em memória e se perde ao reiniciar.
Configure `AEGIS_BASE_URL`, `AEGIS_API_KEY` e `AEGIS_CHAT_PATH`. O payload `{system,prompt}` e a leitura das chaves `answer/content/message` são adaptadores genéricos: alinhe com o contrato real do runtime antes de usar.
