import React, { useEffect } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import validate from "../forms/LoginFormValidation";
import useForm from "../forms/useForm";

function Login() {
  const { values, errors, handleChange, handleSubmit } = useForm(
    callback,
    validate
  );
  const history = useHistory();

  function callback() {}

  useEffect(() => {
    if (
      localStorage.getItem("token") === null ||
      localStorage.getItem("token") === undefined
    ) {
      axios
        .get("http://localhost:3001/login")
        .then((response) => {
          if (response.data.loggedIn === true) {
            history.push("/home");
          } else {
            history.push("/login");
          }
        })
        .catch((error) => console.error(`Error :  ${error}`));
    } else {
      history.push("/home");
    }
  }, [history]);

  const generateLogin = () => {
    if (Object.keys(errors).length === 0) {
      const log = {
        username: values.username,
        password: values.password,
      };
      console.log(errors);
      login(log);
    }
  };

  const login = (log) => {
    //calling getToken to get Jwt backEndToken from Back-End

    axios
      .post("http://localhost:3001/login", {
        username: log.username,
        password: log.password,
      })
      .then((response) => {
        if (!response.data.auth) {
          history.push("/login");
        } else {
          getTokens();
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("fullname", response.data.result[0].fullname);
          localStorage.setItem("username", response.data.result[0].username);
          localStorage.setItem("email", response.data.result[0].email);
          localStorage.setItem("role", response.data.result[0].role);
          history.push("/home");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  //geting Jwt Token from all Back-End Microsevices
  const getTokens = () => {
    axios
      .post("http://localhost:8081/flight/authenticate", {
        username: values.username,
        password: values.password,
      })
      .then((response) => {
        if (response.data !== undefined) {
          localStorage.setItem("backEndTokenFlight", response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
    axios
      .post("http://localhost:8082/fare/authenticate", {
        username: values.username,
        password: values.password,
      })
      .then((response) => {
        if (response.data !== undefined) {
          localStorage.setItem("backEndTokenFare", response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
    axios
      .post("http://localhost:8083/booking/authenticate", {
        username: values.username,
        password: values.password,
      })
      .then((response) => {
        if (response.data !== undefined) {
          localStorage.setItem("backEndTokenBooking", response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
    axios
      .post("http://localhost:8084/checkin/authenticate", {
        username: values.username,
        password: values.password,
      })
      .then((response) => {
        if (response.data !== undefined) {
          localStorage.setItem("backEndTokenCheckIn", response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="section is-fullheight">
      <div className="container">
        <div className="column is-4 is-offset-4">
          <div className="box">
            <form onSubmit={handleSubmit} noValidate>
              <div className="field">
                <label className="label">Username</label>
                <div className="control">
                  <input
                    autoComplete="off"
                    className={`input ${errors.username && "is-danger"}`}
                    type="text"
                    name="username"
                    onChange={handleChange}
                    value={values.username || ""}
                    required
                  />
                  {errors.username && (
                    <p className="help is-danger">{errors.username}</p>
                  )}
                </div>
              </div>
              <div className="field">
                <label className="label">Password</label>
                <div className="control">
                  <input
                    className={`input ${errors.password && "is-danger"}`}
                    type="password"
                    name="password"
                    onChange={handleChange}
                    value={values.password || ""}
                    required
                  />
                </div>
                {errors.password && (
                  <p className="help is-danger">{errors.password}</p>
                )}
              </div>
              <button
                type="submit"
                className="button is-block is-info is-fullwidth"
                onClick={generateLogin}
              >
                Login
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
