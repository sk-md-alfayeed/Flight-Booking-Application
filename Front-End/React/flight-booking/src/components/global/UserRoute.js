import React from "react";
import { Redirect, Route } from "react-router-dom";

function UserRoute({ component: Component, ...rest }) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  return (
    <Route
      {...rest}
      render={(props) => {
        if ((token !== null && role === "USER") || role === "ADMIN") {
          return <Component />;
        } else {
          return (
            <Redirect to={{ pathname: "", state: { from: props.location } }} />
          );
        }
      }}
    />
  );
}

export default UserRoute;
