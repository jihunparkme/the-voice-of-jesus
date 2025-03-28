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

## System architecture

![Result](./src/main/resources/static/images/system-architecture.png 'Result')

## Key Features

- 유튜브 재생목록에서 영상 리스트 추출
- 영상 자막 추출
- 자막 전처리
- 자막 형태소 분석으로 주요 키워드 추출 / [komoran](https://docs.komoran.kr/)
  - word cloud 생성
- 자막 요약 / [gemini](https://gemini.google.com/app)

## Sample

### Sermon List

![Image](https://github.com/user-attachments/assets/71fc8e9f-e6b0-4c90-bb3e-3087ab85b5d4)

### Sermon View

![Image](https://github.com/user-attachments/assets/35281f56-d020-4e6e-82e4-97d6a6fa610c)



