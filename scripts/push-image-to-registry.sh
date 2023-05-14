# shellcheck disable=SC2154
VERSION=latest
docker build --tag registry.thinhlh.com/job_service:"$VERSION" --platform linux/amd64 .
docker push registry.thinhlh.com/job_service:"$VERSION"