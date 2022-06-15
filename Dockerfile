FROM eclipse-temurin:17-alpine

RUN mkdir /etc/bingo-tournament
VOLUME ["/etc/bingo-tournament"]
WORKDIR /etc/bingo-tournament

COPY build/libs/bingo-tournament.jar /usr/bin

CMD ["java", "-jar", "/usr/bin/bingo-tournament.jar"]
