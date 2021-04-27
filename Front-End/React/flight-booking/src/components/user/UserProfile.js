import React, { useEffect, useState } from "react";
import Axios from "axios";

function UserProfile() {
  const oldUsername = localStorage.getItem("username");

  const [fullnameUpd, setFullnameUpd] = useState("");
  const [usernameUpd, setUsernameUpd] = useState("");
  const [emailUpd, setEmailUpd] = useState("");

  useEffect(() => {
    Axios.post("http://localhost:3001/user", {
      username: oldUsername,
    }).then((response) => {
      if (response.data.result[0] !== undefined) {
        setFullnameUpd(response.data.result[0].fullname);
        setUsernameUpd(response.data.result[0].username);
        setEmailUpd(response.data.result[0].email);
      }
    });
  }, [oldUsername]);

  const update = () => {
    Axios.put("http://localhost:3001/update", {
      oldUsername: oldUsername,
      fullname: fullnameUpd,
      email: emailUpd,
    }).then(() => {});
  };

  return (
    <div>
      <br></br>
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            <div className="card-body"></div>
            <form>
              <h3 className="text-center">Update Profile</h3>

              <div className="form-group">
                <label>Username</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Username"
                  name="usernameUpd"
                  value={usernameUpd || ""}
                  disabled="disabled"
                />
              </div>

              <div className="form-group">
                <label>Full name</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Full name"
                  name="fullnameUpd"
                  value={fullnameUpd || ""}
                  onChange={(e) => {
                    setFullnameUpd(e.target.value);
                  }}
                />
              </div>

              <div className="form-group">
                <label>Email address</label>
                <input
                  type="email"
                  className="form-control"
                  placeholder="Enter email"
                  name="emailUpd"
                  value={emailUpd || ""}
                  onChange={(e) => {
                    setEmailUpd(e.target.value);
                  }}
                />
              </div>

              <button onClick={update} className="btn btn-primary btn-block">
                Update
              </button>
            </form>
            <br></br>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserProfile;
