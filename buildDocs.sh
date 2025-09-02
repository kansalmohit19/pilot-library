#!/bin/bash

set -e

if [[ "$OSTYPE" == "darwin"* ]]; then
  SEDOPTION="-i ''"   # macOS
else
  SEDOPTION="-i"      # Linux
fi

DOCS_ROOT=docs

if [ -d "$DOCS_ROOT" ]; then
  rm -r "$DOCS_ROOT"
fi

mkdir -p "$DOCS_ROOT"

cp README.md $DOCS_ROOT/index.md
cp LICENSE.md $DOCS_ROOT/license.md
cp samplelibrary/README.md $DOCS_ROOT/sample-library.md
cp samplelibrary/VIEWMODEL.md $DOCS_ROOT/view-model.md

eval sed $SEDOPTION -e 's/LICENSE.md/license/g' "$DOCS_ROOT/index.md"

