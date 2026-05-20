#!/usr/bin/env bash
# build.sh — environment-aware build and test runner.
#
# Usage: ./build.sh <profile>
#
#   local  — compile + unit tests only (*Test.java via Surefire)
#   dev    — compile + unit tests + integration tests (*IT.java via Failsafe)
#              ProductControllerIT requires Docker (MySQL container)
#   stage  — same as dev
#   prod   — compile + package, all tests skipped
#
# How tests are selected (naming convention):
#   *Test.java  →  Surefire  (unit tests, run in every non-prod profile)
#   *IT.java    →  Failsafe  (integration tests, run only in dev and stage)
#
# To run only a specific test class manually:
#   mvn test -Dtest=ProductServiceTest           # single unit test class
#   mvn verify -Pdev -Dit.test=ProductControllerIT  # single IT class

set -euo pipefail

PROFILE="${1:-}"

usage() {
    echo "Usage: $0 {local|dev|stage|prod}"
    exit 1
}

[ -z "$PROFILE" ] && usage

case "$PROFILE" in

    local)
        echo "==> [local] Running unit tests only (no Docker required)"
        ./mvnw test -Plocal
        ;;

    dev)
        echo "==> [dev] Running all tests — unit + integration (Docker required for MySQL)"
        ./mvnw verify -Pdev
        ;;

    stage)
        echo "==> [stage] Running all tests — unit + integration (Docker required for MySQL)"
        ./mvnw verify -Pstage
        ;;

    prod)
        echo "==> [prod] Building production artifact — all tests skipped"
        ./mvnw package -Pprod
        ;;

    *)
        echo "Unknown profile: '$PROFILE'"
        usage
        ;;

esac
