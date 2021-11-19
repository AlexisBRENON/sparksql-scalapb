FROM hseeberger/scala-sbt:8u242_1.3.7_2.12.10

RUN chmod og+rx /root/
RUN mkdir -p /root/.sbt/ /root/.ivy2/ /root/.cache/
RUN chmod -R og=u /root/.sbt/ /root/.ivy2/ /root/.cache/

RUN useradd --uid 1000 --no-user-group --base-dir /tmp --create-home jenkins
