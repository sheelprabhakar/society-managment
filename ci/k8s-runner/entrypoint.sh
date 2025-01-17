#!/bin/bash
# Copyright 2020 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

REPO_OWNER="sheelprabhakar"
REPO_NAME="society-managment"
ACTIONS_RUNNER_INPUT_URL="https://github.com/sheelprabhakar/society-managment"

#set name for this runner as the hostname
# shellcheck disable=SC2034
# ACTIONS_RUNNER_INPUT_NAME is used by config.sh
ACTIONS_RUNNER_INPUT_NAME="sheel-gharunner"
#get regsistration token for this runnner
echo "https://api.github.com/orgs/${REPO_OWNER}/actions/runners/registration-token"
ACTIONS_RUNNER_INPUT_TOKEN="$(curl -sS --request POST --url "https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/actions/runners/registration-token" --header "authorization: Bearer ${GITHUB_TOKEN}"  --header 'content-type: application/json' | jq -r .token)"
#ACTIONS_RUNNER_INPUT_TOKEN="$(curl -sS --request POST --url "https://api.github.com/orgs/${REPO_OWNER}/actions/runners/registration-token" --header "authorization: Bearer ${GITHUB_TOKEN}"  --header 'content-type: application/json' | jq -r .token)"
#echo $ACTIONS_RUNNER_INPUT_TOKEN
#configure runner
/runner/config.sh --unattended --replace --work "/tmp" --url "$ACTIONS_RUNNER_INPUT_URL" --token "$ACTIONS_RUNNER_INPUT_TOKEN"
#start runner
#https://github.com/actions/runner/issues/246#issuecomment-615293718
/runner/bin/runsvc.sh