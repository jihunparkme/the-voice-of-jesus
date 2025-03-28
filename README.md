# the-voice-of-jesus

매주 유튜브 설교 영상 자막을 추출하고, 요약, 형태소 분석을 통해 설교 내용을 분석합니다.

## Tech Stack

```bash
├── Backend
│   ├── Kotlin 1.9.25
│   ├── JDK 21
│   ├── Springboot 3.4.1
│   ├── gradle 8.11.1
│   ├── Ktor
│   ├── jsoup
│   └── komoran
├── DataBase
│   └── mongodb
├── DevOps
│   ├── AWS EC2 (t2.micro)
│   ├── Docker
│   └── Nginx
├── Monitoring
│   ├── prometheus
│   ├── grafana
│   └── sentry
├── Front
│   ├── thymeleaf
│   ├── JavaScript(ES6)
│   └── CSS
├── SSL
│   ├──cloudflare
├── TEST
│   ├──Junit 5
│   └──springmockk
```

## Key Features

- 유튜브 재생목록에서 동영상 리스트 추출
- 동영상 자막 추출
- 자막 전처리
- 자막 형태소 분석(word cloud 생성) / [komoran](https://docs.komoran.kr/)
- 자막 요약 / [gemini](https://gemini.google.com/app)

## Sample

### List

### View



