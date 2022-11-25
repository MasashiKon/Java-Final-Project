const monsHp = document.getElementById("monsHp");
const monsAttack = document.getElementById("monsAttack");
const monsDefense = document.getElementById("monsDefense");
const monsMagicAttack = document.getElementById("monsMagicAttack");
const monsMagicDefense = document.getElementById("monsMagicDefense");
const monsSpeed = document.getElementById("monsSpeed");

if(monsHp.innerHTML === "") monsHp.innerHTML = 50;
if(monsAttack.innerHTML === "") monsAttack.innerHTML = 50;
if(monsDefense.innerHTML === "") monsDefense.innerHTML = 50;
if(monsMagicAttack.innerHTML === "") monsMagicAttack.innerHTML = 50;
if(monsMagicDefense.innerHTML === "") monsMagicDefense.innerHTML = 50;
if(monsSpeed.innerHTML === "") monsSpeed.innerHTML = 50;

const playerHp = document.getElementById("playerHp");
const playerAttack = document.getElementById("playerAttack");
const playerDefense = document.getElementById("playerDefense");
const playerMagicAttack = document.getElementById("playerMagicAttack");
const playerMagicDefense = document.getElementById("playerMagicDefense");
const playerSpeed = document.getElementById("playerSpeed");

const messageWindow = document.getElementById("messageWindow");

const hpSpin = setInterval(() => {
    playerHp.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const attackSpin = setInterval(() => {
    playerAttack.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const defenseSpin = setInterval(() => {
    playerDefense.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const magicAttackSpin = setInterval(() => {
    playerMagicAttack.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const magicDefenseSpin = setInterval(() => {
    playerMagicDefense.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const speedSpin = setInterval(() => {
    playerSpeed.innerHTML = Math.floor(Math.random() * 200) + 1;
}, 300);

const stopAllBtn = document.getElementById("stopAllBtn");
const stopHpBtn = document.getElementById("stopHpBtn");
const stopAttackBtn = document.getElementById("stopAttackBtn");
const stopDefenseBtn = document.getElementById("stopDefenseBtn");
const stopMagicAttackBtn = document.getElementById("stopMagicAttackBtn");
const stopMagicDefenseBtn = document.getElementById("stopMagicDefenseBtn"); 
const stopSpeedBtn = document.getElementById("stopSpeedBtn");

const userInfo = {
    total: Number.parseInt(document.getElementById("wins").innerHTML) + Number.parseInt(document.getElementById("loses").innerHTML),
    wins: Number.parseInt(document.getElementById("wins").innerHTML),
    loses: Number.parseInt(document.getElementById("loses").innerHTML),
    streak: Number.parseInt(document.getElementById("streak").innerHTML)
}

const player = {
    hp: 0,
    attack: 0,
    defense: 0,
    magicAttack: 0,
    magicDefense: 0,
    speed: 0,
    isDefensing: false
}

const monster = {
    hp: parseInt(monsHp.innerHTML),
    attack: parseInt(monsAttack.innerHTML),
    defense: parseInt(monsDefense.innerHTML),
    magicAttack: parseInt(monsMagicAttack.innerHTML),
    magicDefense: parseInt(monsMagicDefense.innerHTML),
    speed: parseInt(monsSpeed.innerHTML),
    isDefensing: false
}

const config = {
    isSpinfixted: {
        hp: false,
        attack: false,
        defense: false,
        magicAttack: false,
        magicDefense: false,
        speed: false
    },
    isBattling: false 
}

const attackBtn = document.getElementById("attackBtn");
const magicAttackBtn = document.getElementById("magicAttackBtn");
const defenseBtn = document.getElementById("defenseBtn");

const nextBattleBtn = document.getElementById("nextBattleBtn");

attackBtn.addEventListener('click', () => {
    attackBtn.setAttribute('disabled', 'disabled');
    magicAttackBtn.setAttribute('disabled', 'disabled');
    defenseBtn.setAttribute('disabled', 'disabled');
    excuteATurn("attack");
});

magicAttackBtn.addEventListener('click', () => {
    attackBtn.setAttribute('disabled', 'disabled');
    magicAttackBtn.setAttribute('disabled', 'disabled');
    defenseBtn.setAttribute('disabled', 'disabled');
    excuteATurn("magicAttack");
});

defenseBtn.addEventListener('click', () => {
    attackBtn.setAttribute('disabled', 'disabled');
    magicAttackBtn.setAttribute('disabled', 'disabled');
    defenseBtn.setAttribute('disabled', 'disabled');
    excuteATurn("defense");
});

stopAllBtn.addEventListener('click', () => {
    stopHpSpin();
    stopAttackSpin();
    stopDefenseSpin();
    stopMagicAttackSpin();
    stopMagicDefenseSpin();
    stopSpeedSpin();
});

stopHpBtn.addEventListener('click', stopHpSpin);

stopAttackBtn.addEventListener('click', stopAttackSpin);

stopDefenseBtn.addEventListener('click', stopDefenseSpin);

stopMagicAttackBtn.addEventListener('click', stopMagicAttackSpin);

stopMagicDefenseBtn.addEventListener('click', stopMagicDefenseSpin);

stopSpeedBtn.addEventListener('click', stopSpeedSpin);

nextBattleBtn.addEventListener('click', nextBattle);

function stopHpSpin() {
    clearInterval(hpSpin);
    config.isSpinfixted.hp = true;
    player.hp = playerHp.innerHTML;
    stopHpBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function stopAttackSpin() {
    clearInterval(attackSpin);
    config.isSpinfixted.attack = true;
    player.attack = playerAttack.innerHTML;
    stopAttackBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function stopDefenseSpin() {
    clearInterval(defenseSpin);
    config.isSpinfixted.defense = true;
    player.defense = playerDefense.innerHTML;
    stopDefenseBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function stopMagicAttackSpin() {
    clearInterval(magicAttackSpin);
    config.isSpinfixted.magicAttack = true;
    player.magicAttack = playerMagicAttack.innerHTML;
    stopMagicAttackBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function stopMagicDefenseSpin() {
    clearInterval(magicDefenseSpin);
    config.isSpinfixted.magicDefense = true;
    player.magicDefense = playerMagicDefense.innerHTML;
    stopMagicDefenseBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function stopSpeedSpin() {
    clearInterval(speedSpin);
    config.isSpinfixted.speed = true;
    player.speed = playerSpeed.innerHTML;
    stopSpeedBtn.setAttribute('disabled', 'disabled');
    spinChecker();
}

function spinChecker() {
    if(config.isSpinfixted.hp && config.isSpinfixted.attack && config.isSpinfixted.defense && config.isSpinfixted.magicAttack && config.isSpinfixted.magicDefense && config.isSpinfixted.speed) {
        stopAllBtn.remove();
        document.getElementById("buttonsRow").remove();
        document.getElementById("allButtonRow").remove();
        messageWindow.innerHTML = "Battle Start!";
        attackBtn.removeAttribute("hidden");
        magicAttackBtn.removeAttribute("hidden");
        defenseBtn.removeAttribute("hidden");
        config.isBattling = true;
    }
}

async function excuteATurn(command) {
    if(config.isBattling) {
        try {
            if(player.speed === monster.speed) {
                if(Math.floor(Math.random() * 2) === 0) {
                    const playerResult = await new Promise(playerAction);
                    const monsterResult = await new Promise(monsterAction);
                } else {
                    const monsterResult = await new Promise(monsterAction);
                    const playerResult = await new Promise(playerAction);
                }
            } else if(player.speed > monster.speed) {
                const playerResult = await new Promise(playerAction);
                const monsterResult = await new Promise(monsterAction);
            } else {
                const monsterResult = await new Promise(monsterAction);
                const playerResult = await new Promise(playerAction);
            }
            messageWindow.innerHTML = "";
        } catch(error) {
            if(player.hp <= 0) {
                messageWindow.innerHTML = "Lose...";
            } else if(monster.hp <= 0) {
                messageWindow.innerHTML = "You Defeated!"
            } else {
                console.log(error);
            }
        }

    }

    attackBtn.removeAttribute('disabled');
    magicAttackBtn.removeAttribute('disabled');
    defenseBtn.removeAttribute('disabled');

    function playerAction(resolve, reject) {
        switch(command) {
            case "attack":
                messageWindow.innerHTML = "Player's Attack";
                break;
            case "magicAttack":
                messageWindow.innerHTML = "Player's Magic";
                break;
            case "defense":
                messageWindow.innerHTML = "Player is defensing";
                break;
            default:
                messageWindow.innerHTML = "Player is spaced out!"
        }
        setTimeout(() => {
            let damage;
            switch(command) {
                case "attack":
                    damage = Math.ceil(player.attack / monster.defense * 10);
                    monster.hp -= damage;
                    break;
                case "magicAttack":
                    damage = Math.ceil(player.magicAttack / monster.magicDefense * 10);
                    monster.hp -= damage;
                    break;
                case "defense":
                    player.isDefensing = true;
                    player.defense *= 2;
                    player.magicDefense *= 2;
                    damage = 0;
                    break;
                default:
                    reject("Sorry, something wrong. Try again.");
            }
            if(monster.hp <= 0) {
                monsHp.innerHTML = 0;
                messageWindow.innerHTML = "Monster fainted!";
                config.isBattling = false;
                userInfo.total ++;
                userInfo.streak ++;
                userInfo.wins ++;
                document.getElementById("total").innerHTML = userInfo.total;
                document.getElementById("streak").innerHTML = userInfo.streak;
                document.getElementById("wins").innerHTML = userInfo.wins;
                fetch("http://localhost:8080/randombattle", {
                    method: "post",
                    headers: {
                        'Content-Type': "application/json;charset=UTF-8"
                    },
                    redirect: 'follow',
                    body: JSON.stringify(userInfo)
                });
                nextBattleBtn.removeAttribute("hidden");
                setTimeout(() => {
                    reject(0);
                }, 1000);
            } else {
                monsHp.innerHTML -= damage;
                messageWindow.innerHTML =`${damage} damage!`;
                if(player.isDefensing) {
                    player.defense /= 2;
                    player.magicDefense /= 2;
                }
                player.isDefensing = false;
                if(monster.isDefensing){
                    monster.defense /= 2;
                    monster.magicDefense /= 2;
                }
                monster.isDefensing = false;
                setTimeout(() => {
                    resolve(1);
                }, 1000);
            }
        }, 2000);
    };

    function monsterAction(resolve, reject) {
        const monsAction = Math.floor(Math.random() * 2.2);
        switch(monsAction) {
            case 0:
                messageWindow.innerHTML = "Monster's Attack";
                break;
            case 1:
                messageWindow.innerHTML = "Monster's Magic";
                break;
            default:
                messageWindow.innerHTML = "Monster is defensing";
                monster.isDefensing = true;
                monster.defense *= 2;
                monster.magicDefense *= 2;
        }
        setTimeout(() => {
            let damage;
            switch(monsAction) {
                case 0:    
                    damage = Math.ceil(monster.defense / player.attack * 10);
                    player.hp -= damage;
                    break;
                case 1:
                    damage = Math.ceil(monster.magicDefense / player.magicAttack * 10);
                    player.hp -= damage;
                    break;
                case 2:
                    damage = 0;
                    break;
                default:
                    reject("Sorry, something wrong. Try again.");
            }
            if(player.hp <= 0) {
                playerHp.innerHTML = 0;
                messageWindow.innerHTML = "Lose...";
                config.isBattling = false;
                userInfo.total++;
                userInfo.streak = 0;
                userInfo.loses++;
                document.getElementById("total").innerHTML = userInfo.total;
                document.getElementById("streak").innerHTML = userInfo.streak;
                document.getElementById("loses").innerHTML = userInfo.loses;
                fetch("http://localhost:8080/randombattle", {
                    method: "post",
                    headers: {
                        'Content-Type': "application/json;charset=UTF-8"
                    },
                    redirect: 'follow',
                    body: JSON.stringify(userInfo)
                });
                nextBattleBtn.removeAttribute("hidden");
                setTimeout(() => {
                    reject(0);
                }, 1000);
            } else {
                playerHp.innerHTML -= damage;
                messageWindow.innerHTML = `${damage} damage!`;
                if(player.isDefensing) {
                    player.defense /= 2;
                    player.magicDefense /= 2;
                }
                player.isDefensing = false;
                if(monster.isDefensing){
                    monster.defense /= 2;
                    monster.magicDefense /= 2;
                }
                monster.isDefensing = false;
                setTimeout(() => {
                    resolve(1);
                }, 1000)
            }
        }, 2000);
    }
}

function nextBattle() {
    window.location.replace("http://localhost:8080/randombattle");
}

// const logoutBtn = document.getElementById("logoutBtn");
// logoutBtn.addEventListener("submit", logout);

// function logout() {
//     fetch("http://localhost:8080/logout")
//         .then(response => {
//             if(response.redirected) {
//                 console.log(response.url);
//                 window.location.href = response.url;
//             }
//         })
//         .catch(error => console.error(error));
// }