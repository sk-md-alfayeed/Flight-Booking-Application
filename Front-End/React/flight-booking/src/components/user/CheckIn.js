import React, { useState } from "react";
import { useHistory } from "react-router";

import FlightService from "../../services/FlightService";
import validate from "../forms/CheckInValidation";

import useForm from "../forms/useForm";

function CheckIn() {
  const backEndTokenBooking = localStorage.getItem("backEndTokenBooking");
  const backEndTokenCheckIn = localStorage.getItem("backEndTokenCheckIn");

  const { values, errors, handleChange, handleSubmit } = useForm(
    callback,
    validate
  );

  const [checkIn, setCheckIn] = useState({});
  const [passengerList, setPassengerList] = useState([]);
  const history = useHistory();

  function callback() {}

  const searchPnr = () => {
    if (Object.keys(errors).length === 0) {
      let search = {
        pnrNo: values.pnrNo,
        email: values.email,
      };
      FlightService.getCheckIn(search, backEndTokenCheckIn)
        .then((response) => {
          setCheckIn(response.data);
          setPassengerList(response.data.passengerList);
        })
        .catch((error) => {
          console.error(`Error :  ${error}`);
        });
    }
  };

  const confirmPassenger = () => {
    FlightService.getBooking(checkIn.id, backEndTokenBooking)
      .then((response) => {
        let myBooking = response.data;
        myBooking.passengerList = passengerList;
        console.log(passengerList);
        addCheckIn(myBooking);
      })
      .catch((error) => console.error(`Error :  ${error}`));

    FlightService.getBooking(checkIn.id, backEndTokenBooking)
      .then((response) => {
        let myBooking = response.data;
        myBooking.passengerList = passengerList;
        console.log(passengerList);
        addCheckIn(myBooking);
      })
      .catch((error) => console.error(`Error :  ${error}`));
    // addCheckIn();
  };

  const addCheckIn = (myBooking) => {
    FlightService.updateBooking(myBooking, backEndTokenBooking)
      .then((response) => {
        history.push("/checkin");
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  const handleChangeInput = (id, event) => {
    const newPassengerList = passengerList.map((i) => {
      if (id === i.id) {
        i[event.target.name] = event.target.value;
      }
      return i;
    });
    setPassengerList(newPassengerList);
  };

  return (
    <div>
      <div className="container">
        <div className="containerCardCheckIn">
          <div className="upperCheckIn">
            <p>Search PNR No</p>

            <form
              className="row align-items-start"
              onSubmit={handleSubmit}
              noValidate
            >
              <div className="col-sm">
                <label> PNR No </label>
                <input
                  autoComplete="off"
                  className={`input ${errors.pnrNo && "is-danger"}`}
                  type="text"
                  placeholder="PNR No"
                  name="pnrNo"
                  onChange={handleChange}
                  value={values.pnrNo || ""}
                  required
                />
                {errors.pnrNo && (
                  <p className="help is-danger">{errors.pnrNo}</p>
                )}
              </div>

              <div className="col-sm">
                <label> Email </label>
                <input
                  autoComplete="off"
                  className={`input ${errors.email && "is-danger"}`}
                  type="text"
                  placeholder="Email"
                  name="email"
                  onChange={handleChange}
                  value={values.email || ""}
                  required
                />
                {errors.email && (
                  <p className="help is-danger">{errors.email}</p>
                )}
              </div>

              <div className="col-sm">
                <label className="text-white"> Button </label>
                <button
                  id="search"
                  type="submit"
                  className="btn-block secondary-button button cursor-pointer bold"
                  onClick={searchPnr}
                >
                  Search
                </button>
                {errors.pnrNo && <p className="help text-white">{"."}</p>}
              </div>
            </form>
          </div>
        </div>
        <br></br>

        {passengerList.length !== 0
          ? passengerList.map((passenger) => (
              <div className="containerPassengerCheckIn">
                <div className="upperPassengerCheckIn">
                  <div className="row align-items-start" key={passenger.id}>
                    <div className="col-sm">
                      <label> Flight </label>
                      <p> {checkIn.id}</p>
                    </div>
                    <div className="col-sm">
                      <label> Passenger Id </label>
                      <p> {passenger.id}</p>
                    </div>
                    <div className="col-sm">
                      <label> Passenger Name </label>
                      <p>
                        {" "}
                        {passenger.firstName +
                          " " +
                          passenger.middleName +
                          " " +
                          passenger.lastName}
                      </p>
                    </div>
                    <div className="col-sm">
                      <label> Seat No </label>
                      {passenger.seatNo ? (
                        <select
                          className="form-control"
                          name="seatNo"
                          value={passenger.seatNo || ""}
                          onChange={(e) => {
                            handleChangeInput(passenger.id, e);
                          }}
                        >
                          <option value="null">-</option>
                          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((seat) => (
                            <option key={seat} value={seat}>
                              {seat}
                            </option>
                          ))}
                        </select>
                      ) : (
                        <select
                          className="form-control"
                          name="seatNo"
                          value={passenger.seatNo || ""}
                          onChange={(e) => {
                            handleChangeInput(passenger.id, e);
                          }}
                          // disabled="disable"
                        >
                          <option value="">-</option>
                          {[1, 2, 3].map((seat) => (
                            <option key={seat} value={seat}>
                              {seat}
                            </option>
                          ))}
                        </select>
                      )}
                    </div>

                    {passenger.seatNo === "null" ? (
                      <div className="col-sm">
                        <label>Checked In</label>
                        <p>No</p>
                      </div>
                    ) : (
                      <div className="col-sm">
                        <label>Checked In</label>
                        <p>Yes</p>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            ))
          : null}

        {passengerList.length !== 0 ? (
          <div className="text-center">
            <button
              className="button is-success"
              onClick={() => confirmPassenger()}
            >
              {" "}
              Confirm
            </button>
          </div>
        ) : null}
        <br></br>
      </div>
    </div>
  );
}

export default CheckIn;
