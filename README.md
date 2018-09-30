# itmo-oop

this repo consist of practical materials of oop

[![Build Status](https://travis-ci.com/egoarka/itmo-oop.svg?branch=master)](https://travis-ci.com/egoarka/itmo-oop)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com:app)](https://sonarcloud.io/dashboard/index/com.github.noraui:noraui)
[![SonarCloud Coverage](https://sonarcloud.io/api/badges/measure?key=com:app&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=com.github.noraui:noraui)
[![SonarCloud Bugs](https://sonarcloud.io/api/badges/measure?key=com:app&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=com:app)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/badges/measure?key=com:app&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=com:app)


**convert md to docx** (docker is required)
```bash
docker run -v `pwd`:/source jagregory/pandoc -f markdown_github -t docx stageN.md -o stageN.md
```
