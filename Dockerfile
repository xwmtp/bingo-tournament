FROM eclipse-temurin:17-alpine

RUN mkdir /etc/bingo-tournament
VOLUME ["/etc/bingo-tournament"]
WORKDIR /etc/bingo-tournament
EXPOSE 8080

COPY build/libs/bingo-tournament.jar /usr/bin

CMD ["java", "-jar", "/usr/bin/bingo-tournament.jar"]
