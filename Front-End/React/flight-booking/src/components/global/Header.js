import React from "react";
import { Link, withRouter } from "react-router-dom";

function Header() {
  //   let isLoggedIn = true;
  //   const token = localStorage.getItem("token");

  //   if (token === null) {
  //     isLoggedIn = false;
  //   }

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("fullname");
    localStorage.removeItem("username");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
  };

  return (
    <div>
      <nav className="navbar navbar-inverse navbar-dark bg-dark">
        <div className="container-fluid">
          <div className="navbar-header">
            <Link className="navbar-brand" to={"/home"}>
              <h2>
                Alpha Flights{" "}
                <span className="h6 text-muted">
                  {localStorage.getItem("fullname")}
                </span>
              </h2>
            </Link>
          </div>

          {localStorage.getItem("token") &&
          localStorage.getItem("role") === "USER" ? (
            <div>
              <ul className="nav inline-form">
                {/* <li className="nav-item ">
                  <Link className="nav-link" to={"/user_profile"}>
                    Profile
                  </Link>
                </li> */}
                <li className="nav-item">
                  <Link className="nav-link" to={"/user_booking"}>
                    Bookings
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/checkin"}>
                    CheckIn
                  </Link>
                </li>
                <li className="nav-item">
                  <Link onClick={logout} className="nav-link" to={"/home"}>
                    Logout
                  </Link>
                </li>
              </ul>
            </div>
          ) : null}
          {localStorage.getItem("token") &&
          localStorage.getItem("role") === "ADMIN" ? (
            <div>
              <ul className="nav inline-form">
                <li className="nav-item">
                  <Link className="nav-link" to={"/manage_flight"}>
                    Manage Flights
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/manage_fare"}>
                    Manage Fares
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/manage_booking"}>
                    Manage Bookings
                  </Link>
                </li>
                <li className="nav-item">
                  <Link onClick={logout} className="nav-link" to={"/home"}>
                    Logout
                  </Link>
                </li>
              </ul>
            </div>
          ) : null}
          {!localStorage.getItem("token") ? (
            <div>
              <ul className="nav inline-form">
                <li className="nav-item">
                  <Link className="nav-link" to={"/login"}>
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/register"}>
                    Sign up
                  </Link>
                </li>
              </ul>
            </div>
          ) : null}
        </div>
      </nav>
    </div>
  );
}

export default withRouter(Header);
