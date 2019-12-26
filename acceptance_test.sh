#!/bin/bash
test $(curl http://calculator:8765/sum?a=1\&b=2) -eq 3