# itmo-oop

this repo consist of practical materials of oop

[![Build Status](https://travis-ci.com/egoarka/itmo-oop.svg?branch=master)](https://travis-ci.com/egoarka/itmo-oop)

**convert md to docx** (docker is required)
```bash
docker run -v `pwd`:/source jagregory/pandoc -f markdown_github -t docx stageN.md -o stageN.md
```
