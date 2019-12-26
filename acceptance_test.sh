#!/bin/bash
test $(curl http://calculator:8080/sum?a=1\&b=2) -eq 3