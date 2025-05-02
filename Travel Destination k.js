let favoritePlaces = [];

function addToFavorites(event, title, img, desc) {
    event.stopPropagation();
    if (!favoritePlaces.find(place => place[0] === title)) {
        favoritePlaces.push([title, img, desc]);
        renderFavorites();
        alert(`${title} added to favorites!`);
    } else {
        alert(`${title} is already in favorites.`);
    }
}

function renderFavorites() {
    const section = document.getElementById('favorites');
    section.innerHTML = '<button class="clear-btn" onclick="clearFavorites()">Clear Favorites</button>';
    favoritePlaces.forEach(([title, img, desc]) => {
        const card = document.createElement('div');
        card.className = 'card';
        card.innerHTML = `
            <img src="${img}" alt="${title}">
            <div class="card-content">
                <strong>${title}</strong>
                <p class="desc">${desc}</p>
            </div>
        `;
        section.appendChild(card);
    });
}

function clearFavorites() {
    favoritePlaces = [];
    renderFavorites();
    alert('Favorites list has been cleared.');
}

function switchTab(tabId) {
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
        section.style.display = 'none';
    });
    const activeSection = document.getElementById(tabId);
    activeSection.classList.add('active');
    activeSection.style.display = tabId === 'home' ? 'block' : 'grid';

    document.querySelectorAll('nav a').forEach(link => link.classList.remove('active'));
    const clickedTab = Array.from(document.querySelectorAll('nav a')).find(link =>
        link.textContent.toLowerCase().includes(tabId)
    );
    if (clickedTab) clickedTab.classList.add('active');
}

function showSignIn() {
    document.getElementById("sign-in-form").style.display = "block";
    document.getElementById("sign-up-form").style.display = "none";
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.tab')[0].classList.add('active');
}

function showSignUp() {
    document.getElementById("sign-in-form").style.display = "none";
    document.getElementById("sign-up-form").style.display = "block";
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.tab')[1].classList.add('active');
}

function login() {
    const user = document.getElementById('username').value.trim();
    const pass = document.getElementById('password').value.trim();
    const remember = document.getElementById('rememberMe').checked;

    if ((user === "admin" && pass === "1234") || (localStorage.getItem(user) === pass)) {
        alert("Login successful!");
        document.getElementById('loginPage').style.display = 'none';
        document.getElementById('mainContent').style.display = 'block';

        if (remember) {
            localStorage.setItem("rememberUser", user);
        } else {
            localStorage.removeItem("rememberUser");
        }
    } else {
        alert("Invalid username or password! Try again.");
    }
}

function signup() {
    const newUser = document.getElementById('new-username').value.trim();
    const newPass = document.getElementById('new-password').value.trim();

    if (newUser && newPass) {
        localStorage.setItem(newUser, newPass);
        alert("Account created successfully! Please Sign In.");
        showSignIn();
    } else {
        alert("Please fill out both fields to sign up.");
    }
}

function forgotPassword() {
    const email = prompt("Enter your registered email (simulation):");
    if (email) {
        alert("Password reset link sent to " + email + " (simulation)");
    }
}


window.onload = function() {
    const rememberedUser = localStorage.getItem("rememberUser");
    if (rememberedUser && localStorage.getItem(rememberedUser)) {
        document.getElementById('loginPage').style.display = 'none';
        document.getElementById('mainContent').style.display = 'block';
    }
}
