/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package academy.devonline.tictactoe.model.config;

import academy.devonline.tictactoe.component.ComputerMoveStrategy;
import academy.devonline.tictactoe.component.strategy.FirstMoveToTheCenterComputerMoveStrategy;
import academy.devonline.tictactoe.component.strategy.PreventUserWinComputerMoveStrategy;
import academy.devonline.tictactoe.component.strategy.RandomComputerMoveStrategy;
import academy.devonline.tictactoe.component.strategy.WinNowComputerMoveStrategy;
import academy.devonline.tictactoe.component.strategy.WinOnTheNextStepComputerMoveStrategy;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public enum Level {

    LEVEL1(new ComputerMoveStrategy[]{
            new FirstMoveToTheCenterComputerMoveStrategy(),
            new RandomComputerMoveStrategy()
    }),

    LEVEL2(new ComputerMoveStrategy[]{
            new WinNowComputerMoveStrategy(),
            new PreventUserWinComputerMoveStrategy(),
            new FirstMoveToTheCenterComputerMoveStrategy(),
            new RandomComputerMoveStrategy()
    }),

    LEVEL3(new ComputerMoveStrategy[]{
            new WinNowComputerMoveStrategy(),
            new PreventUserWinComputerMoveStrategy(),
            new WinOnTheNextStepComputerMoveStrategy(),
            new FirstMoveToTheCenterComputerMoveStrategy(),
            new RandomComputerMoveStrategy()
    });

    private final ComputerMoveStrategy[] strategies;

    Level(final ComputerMoveStrategy[] strategies) {
        this.strategies = strategies;
    }

    public ComputerMoveStrategy[] getStrategies() {
        return strategies.clone();
    }
}
