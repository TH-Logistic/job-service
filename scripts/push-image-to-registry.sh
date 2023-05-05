# shellcheck disable=SC2154
VERSION=latest
docker build --tag www.thinhlh.com/job_service:"$VERSION" --platform linux/amd64 .
docker push www.thinhlh.com/job_service:"$VERSION"