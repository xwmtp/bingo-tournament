FROM eclipse-temurin:17-alpine

COPY build/libs/bingo-tournament.jar /usr/bin

RUN mkdir /etc/bingo-tournament
VOLUME ["/etc/bingo-tournament"]
WORKDIR /etc/bingo-tournament

CMD ["java", "-jar", "/usr/bin/bingo-tournament.jar"]
