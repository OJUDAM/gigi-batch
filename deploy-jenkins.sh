#!/bin/sh

if [ -z $1 ]
then
    echo -e ">> 첫번째 인자가 비었습니다.\n"
    echo ">> 첫번째 인자값은 profile명 입니다."
elif [ $1 != "dev" ] && [ $1 != "prod" ]; then
    echo ">> 첫번째 인자 값은 dev(개발) or prod(운영)로 입력해주세요."
else
    cd /var/lib/jenkins/workspace/gigi

    sudo ./gradlew bootJar

    cd ./build/libs

    sudo scp -i ../../../key/gigi.pem gigi.war ubuntu@158.180.68.72:/home/ubuntu/gigi/batch
    sudo ssh -i ../../../key/gigi.pem -t ubuntu@158.180.68.72 -T "cd /home/ubuntu/gigi/batch ; bash;" "sudo ./gigi-start.sh gigiScheduler.jar $1"

    cd ../..
fi
