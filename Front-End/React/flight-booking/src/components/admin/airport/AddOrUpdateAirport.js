import React, { useState, useEffect } from "react";
import { useParams, useHistory } from "react-router-dom";
import FlightService from "../../../services/FlightService";

function AddOrUpdateAirport() {
  const backEndTokenFlight = localStorage.getItem("backEndTokenFlight");
  const history = useHistory();
  const { id } = useParams();
  const [airportId, setAirportId] = useState("");
  const [airportCode, setAirportCode] = useState("");
  const [airportName, setAirportName] = useState("");

  useEffect(() => {
    if (id === "add") {
      return;
    } else {
      FlightService.getAirport(id)
        .then((response) => {
          let airport = response.data;
          setAirportId(airport.id);
          setAirportCode(airport.airportCode);
          setAirportName(airport.airportName);
        })
        .catch((error) => console.error(`Error :  ${error}`));
    }
  }, [id]);

  const save = () => {
    let airport = {
      id: airportId,
      airportCode: airportCode,
      airportName: airportName,
    };
    if (id === "add") {
      FlightService.addAirport(airport, backEndTokenFlight)
        .then((res) => {
          history.push("/add_or_update_airport/add");
        })
        .catch((error) => console.error(`Error :  ${error}`));
    } else {
      FlightService.updateAirport(airport, backEndTokenFlight)
        .then((res) => {
          history.push("/add_or_update_airport/:id");
        })
        .catch((error) => console.error(`Error :  ${error}`));
    }
  };

  const getTitle = () => {
    if (id === "add") {
      return <h3 className="text-center">Add Airport</h3>;
    } else {
      return <h3 className="text-center">Update Airport</h3>;
    }
  };

  return (
    <div>
      <br></br>
      <div className="container">
        <div className="containerCardSearch">
          <div className="upperSearch">
            <div className="row"></div>
            <form>
              {getTitle()}

              <div className="col-sm">
                <label>Id</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Airport Id"
                  name="airpordid"
                  value={airportId || ""}
                  onChange={(e) => {
                    setAirportId(e.target.value);
                  }}
                />
              </div>

              <div className="col-sm">
                <label>Airport Code</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Airport Code"
                  name="airportCode"
                  value={airportCode || ""}
                  onChange={(e) => {
                    setAirportCode(e.target.value);
                  }}
                />
              </div>

              <div className="col-sm">
                <label>Airport Name</label>
                <input
                  type="email"
                  className="form-control"
                  placeholder="Airport Name"
                  name="airportName"
                  value={airportName || ""}
                  onChange={(e) => {
                    setAirportName(e.target.value);
                  }}
                />
              </div>
              <br></br>
              <div className="col-sm">
                {" "}
                <button onClick={save} className="btn btn-primary btn-block">
                  Add
                </button>
              </div>
            </form>
            <br></br>
          </div>
        </div>
      </div>
    </div>
  );
}
export default AddOrUpdateAirport;
