#!/bin/bash

set -e

DOCS_ROOT=docs

if [ -d "$DOCS_ROOT" ]; then
  rm -r "$DOCS_ROOT"
fi

mkdir -p "$DOCS_ROOT"

cp README.md $DOCS_ROOT/index.md
cp samplelibrary/README.md $DOCS_ROOT/sample-library.md
cp samplelibrary/VIEWMODEL.md $DOCS_ROOT/view-model.md

