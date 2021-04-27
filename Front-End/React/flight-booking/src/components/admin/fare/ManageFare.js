import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import FlightService from "../../../services/FlightService";
// import useForm from "../../forms/useForm";
// import validate from "../../forms/FlightValidation";

function ManageFare() {
  const backEndTokenFare = localStorage.getItem("backEndTokenFare");
  const history = useHistory();

  const [fares, setFares] = useState([]);

  // const { values, errors, handleChange, handleSubmit } = useForm(
  //   callback,
  //   validate
  // );

  useEffect(() => {
    let unmounted = false;
    FlightService.getAllFares()
      .then((response) => {
        if (response.data !== null) {
          if (!unmounted) {
            setFares(response.data);
          }
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));
    return () => {
      unmounted = true;
    };
  }, []);

  // function callback() {}

  //update
  const updateFare = (id) => {
    const updatedFare =
      id !== "" ? fares.find((element) => element.id === id) : {};
    console.log(updatedFare);
    FlightService.updateFare(updatedFare, backEndTokenFare)
      .then((res) => {
        history.push("/manage_fare");
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  const deleteFare = (id) => {
    FlightService.deleteFare(id, backEndTokenFare)
      .then(() => {
        setFares(fares.filter((fare) => fare.id !== id));
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  const handleChangeInput = (id, event) => {
    const newFares = fares.map((i) => {
      if (id === i.id) {
        i[event.target.name] = event.target.value;
      }
      return i;
    });
    setFares(newFares);
  };
  return (
    <div>
      <h1 className="text-center"> Fares List</h1>
      <br></br>
      {fares.length !== 0 ? (
        fares.map((fare, idx) => (
          <div key={idx} className="containerFareU">
            <div className="upperFareU">
              <div className="cards align-items-end">
                <div className="col-sm">
                  <label> Fare Id </label>
                  <input
                    type="text"
                    placeholder="Fare Id"
                    name="id"
                    className="form-control"
                    value={fare.id || ""}
                    disabled="disable"
                  />
                </div>

                <div className="col-sm">
                  <label> Flight Name </label>
                  <input
                    type="text"
                    placeholder="Flight Name"
                    name="flightName"
                    className="form-control"
                    value={fare.flightName || ""}
                    disabled="disable"
                  />
                </div>

                <div className="col-sm">
                  <label> Flight Fare </label>
                  <input
                    autoComplete="off"
                    // className={`input ${errors.departureDate && "is-danger"}`}
                    className="form-control"
                    type="text"
                    placeholder="Flight Fare"
                    name="flightFare"
                    value={fare.flightFare || ""}
                    onChange={(e) => {
                      handleChangeInput(fare.id, e);
                    }}
                    required
                  />
                  {/* {errors.flightFare && (
                    <p className="help is-danger">{errors.flightFare}</p>
                  )} */}
                </div>

                <div className="col-sm">
                  <button
                    id="update"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={(e) => {
                      updateFare(fare.id);
                    }}
                  >
                    Update
                  </button>
                </div>

                <div className="col-sm">
                  <button
                    id="delete"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={() => deleteFare(fare.id)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))
      ) : (
        <div className="text-center">
          <h5>No Fare Available</h5>
        </div>
      )}
    </div>
  );
}

export default ManageFare;
