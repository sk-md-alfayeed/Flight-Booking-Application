const express = require("express");
const mysql = require("mysql");
const cors = require("cors");

const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const session = require("express-session");

const bcrypt = require("bcrypt");
const saltRounds = 10;

const app = express();

const jwt = require("jsonwebtoken");

app.use(express.json());
app.use(
  cors({
    origin: ["http://localhost:3000"],
    methods: ["GET", "POST", "PUT"],
    credentials: true,
  })
);
app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(
  session({
    key: "userId",
    secret: "subscribe",
    resave: false,
    saveUninitialized: false,
    cookie: {
      expires: 60 * 60 * 24,
    },
  })
);

const db = mysql.createConnection({
  user: "root",
  host: "localhost",
  password: "password",
  database: "flightauth",
});

//JWT Authorization
const verifyJWT = (req, res, next) => {
  const token = req.headers["x-access-token"];

  if (!token) {
    res.send("We need a token");
  } else {
    jwt.verify(token, "jwtSecretAlfayeed", (err, decoded) => {
      if (err) {
        res.json({ auth: false, message: "You failed to authenticate" });
      } else {
        req.username = decoded.sub;
        next();
      }
    });
  }
};

app.post("/register", (req, res) => {
  const fullname = req.body.fullname;
  const username = req.body.username;
  const email = req.body.email;
  const password = req.body.password;
  const role = "USER";

  bcrypt.hash(password, saltRounds, (err, hash) => {
    if (err) {
      console.log(err);
    }

    db.query(
      "INSERT INTO users (fullname, username, email, password, role) VALUES (?,?,?,?,?)",
      [fullname, username, email, hash, role],
      (err, result) => {
        console.log("User added with username : " + req.body.username);
      }
    );
  });
});

app.put("/update", (req, res) => {
  const oldUsername = req.body.oldUsername;
  const fullname = req.body.fullname;
  const email = req.body.email;

  console.log(req.body);

  db.query(
    "UPDATE users SET fullname = ?, email = ? WHERE username = ?",
    [fullname, email, oldUsername],
    (err, result) => {
      console.log("User updated : " + result.username);
    }
  );
});

app.get("/isUserAuth", verifyJWT, (req, res) => {
  res.send("You are an authenticated person");
});

app.get("/login", (req, res) => {
  if (req.session.user) {
    console.log(req.session.user);
    res.send({ loggedIn: true, user: req.session.user });
  } else {
    res.send({ loggedIn: false });
  }
});

app.post("/user", (req, res) => {
  const username = req.body.username;
  db.query(
    "SELECT * FROM users WHERE username = ?;",
    username,
    (err, result) => {
      if (err) {
        res.send({ err: err });
      } else {
        res.send({ result: result });
      }
    }
  );
});

app.post("/login", (req, res) => {
  const username = req.body.username;
  const password = req.body.password;

  db.query(
    "SELECT * FROM users WHERE username = ?;",
    username,
    (err, result) => {
      if (err) {
        res.send({ err: err });
      }

      if (result.length > 0) {
        bcrypt.compare(password, result[0].password, (error, response) => {
          if (response) {
            const sub = result[0].username;
            const token = jwt.sign({ sub }, "jwtSecretAlfayeed", {
              expiresIn: "1h",
            });
            req.session.user = result;

            res.json({ auth: true, token: token, result: result });

            console.log(req.session.user);
          } else {
            res.json({
              auth: false,
              message: "Wrong username/password combination",
            });
          }
        });
      } else {
        res.json({ auth: false, message: "User doesn't exist" });
      }
    }
  );
});

app.listen(3001, () => {
  console.log("Auth Server is Running");
});
