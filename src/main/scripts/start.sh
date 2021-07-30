#!/usr/bin/env sh
#
# Copyright (c) 2019. http://devonline.academy
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
########################################################################################################################
# The sh script to start the tic-tac-toe game for Unix systems                                                         #
#                                                                                                                      #
# @author devonline                                                                                                    #
# @email  devonline.academy@gmail.com                                                                                  #
########################################################################################################################

java -jar ${project.build.finalName}.jar
echo "Press enter to continue . . ."
read -r test
