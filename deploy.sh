export AWS_REGION=ap-southeast-1
export AWS_REGISTRY=215314751514.dkr.ecr.ap-southeast-1.amazonaws.com
export APP_NAME=be-side
export TASK_NAME=tks-be-side
export CLUSTER_NAME=be-side

docker --version
mvn clean install -DskipTests
docker build -t ${APP_NAME} .

aws --version
docker --version

aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin ${AWS_REGISTRY}
docker tag ${APP_NAME}:latest ${AWS_REGISTRY}/${APP_NAME}:latest
docker push ${AWS_REGISTRY}/${APP_NAME}:latest

aws --version
# export CLUSTER_NAME=$(aws ecs list-clusters --query clusterArns[0] --output text)Edunext-lite-user
export RUNNING_TASK=$(aws ecs list-tasks --cluster ${CLUSTER_NAME} --query taskArns[0] --output text) 
echo ${RUNNING_TASK}
# Get first running instance
export RUNNING_INSTANCE=$( aws ecs list-container-instances --cluster ${CLUSTER_NAME} --query containerInstanceArns[0] --output text)
echo ${RUNNING_INSTANCE}

if [ ${RUNNING_TASK} != "None" ] 
then
    echo "Shut down old task" 
    aws ecs stop-task --task ${RUNNING_TASK} --cluster ${CLUSTER_NAME}
fi
# Restart the task
aws ecs start-task --task-definition ${TASK_NAME} --container-instances ${RUNNING_INSTANCE} --cluster ${CLUSTER_NAME}
