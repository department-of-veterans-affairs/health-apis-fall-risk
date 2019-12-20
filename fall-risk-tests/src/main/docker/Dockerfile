
FROM vasdvp/health-apis-maven:3.6-jdk-12

COPY maven/ /fall-risk-tests
RUN chmod 755 /fall-risk-tests/*.sh
ENTRYPOINT ["/fall-risk-tests/entrypoint.sh"]
CMD []
