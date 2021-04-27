import React, { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import FlightService from "../../../services/FlightService";

function UpdateBooking() {
  const { id } = useParams();

  const username = localStorage.getItem("username");
  const backEndTokenBooking = localStorage.getItem("backEndTokenBooking");

  const history = useHistory();
  const [booking, setBooking] = useState({});
  const [passengerList, setPassengerList] = useState([]);

  const [flight, setFlight] = useState({});

  useEffect(() => {
    FlightService.getBooking(id, backEndTokenBooking)
      .then((response) => {
        setBooking(response.data);
        setFlight(response.data.flight);
        setPassengerList(response.data.passengerList);
      })
      .catch((error) => console.error(`Error :  ${error}`));
  }, [id, backEndTokenBooking]);

  const updateBooking = () => {
    let myBooking = booking;

    myBooking.passengerList = passengerList;

    FlightService.updateBooking(myBooking, backEndTokenBooking).then((res) => {
      history.push("/manage_booking");
    });
  };

  const cancel = () => {
    history.push("/manage_booking");
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
      <br></br>
      {username === booking.userId || username === "admin" ? (
        <div className="container">
          {Object.keys(flight).length !== 0 ? (
            <div className="containerBM">
              <div className="upperBM">
                <h2 className="text-center">{booking.id}</h2>
                <br></br>
                <h3>
                  <div className="row align-items-start text-center">
                    <div className="col-sm">
                      {" "}
                      <span>{booking.flight.departureTime}</span>
                      <br />
                      {booking.flight.departureAirport.airportName}
                    </div>
                    <div className="col-sm">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 20 20"
                      >
                        <polygon points="16.172 9 10.101 2.929 11.515 1.515 20 10 19.293 10.707 11.515 18.485 10.101 17.071 16.172 11 0 11 0 9" />
                      </svg>
                    </div>
                    <div className="col-sm">
                      {" "}
                      <span>{booking.flight.arrivalTime}</span>
                      <br />
                      {booking.flight.destinationAirport.airportName}
                    </div>

                    <div className="col-sm">
                      <span>{"Departure Date"}</span>
                      <br />
                      {booking.flight.departureDate}
                    </div>

                    <div className="col-sm">
                      <span>{"Arrival Date"}</span>
                      <br />
                      {booking.flight.arrivalDate}
                    </div>

                    <div className="col-sm">
                      <h2>{"\u20B9" + booking.flight.fare.flightFare}</h2>
                    </div>
                  </div>
                  <br></br>
                  {passengerList.length !== 0
                    ? passengerList.map((passenger, idx) => (
                        <div key={idx} className="containerPassenger">
                          <div className="upperPassenger">
                            <h6>{passenger.id}</h6>
                            <div className="row align-items-start">
                              <div className="col-sm">
                                <label> First Name </label>
                                <input
                                  type="text"
                                  className="form-control"
                                  name="firstName"
                                  value={passenger.firstName || ""}
                                  onChange={(e) => {
                                    handleChangeInput(passenger.id, e);
                                  }}
                                ></input>
                              </div>

                              <div className="col-sm">
                                <label> Middle Name </label>
                                <input
                                  type="text"
                                  placeholder="Middle Name"
                                  name="middleName"
                                  className="form-control"
                                  value={passenger.middleName || ""}
                                  onChange={(e) => {
                                    handleChangeInput(passenger.id, e);
                                  }}
                                />
                              </div>

                              <div className="col-sm">
                                <label> Last Name </label>
                                <input
                                  type="text"
                                  placeholder="Last Name"
                                  name="lastName"
                                  className="form-control"
                                  value={passenger.lastName || ""}
                                  onChange={(e) => {
                                    handleChangeInput(passenger.id, e);
                                  }}
                                />
                              </div>

                              <div className="col-sm">
                                <label> Age </label>
                                <input
                                  type="text"
                                  min={new Date().toISOString().slice(0, 10)}
                                  placeholder="Age"
                                  name="age"
                                  className="form-control"
                                  value={passenger.age || ""}
                                  onChange={(e) => {
                                    handleChangeInput(passenger.id, e);
                                  }}
                                />
                              </div>

                              <div className="col-sm">
                                <label>Gender</label>
                                <select
                                  className="form-control"
                                  name="gender"
                                  value={passenger.gender || ""}
                                  onChange={(e) => {
                                    handleChangeInput(passenger.id, e);
                                  }}
                                >
                                  <option
                                    placeholder="Prefer not to say"
                                    value={"Prefer not to say"}
                                  >
                                    -
                                  </option>
                                  <option value="Female">Female</option>
                                  <option value="Male">Male</option>
                                  <option value="Others">Others</option>
                                </select>
                              </div>
                            </div>
                            <br></br>
                            <div className="col-sm">
                              <button
                                id="update"
                                className="btn-block secondary-button button cursor-pointer bold"
                                onClick={(e) => {
                                  updateBooking(booking.id);
                                }}
                              >
                                Update
                              </button>
                            </div>
                            <br></br>
                            <div className="col-sm">
                              <button
                                id="cancel"
                                className="btn-block secondary-button button cursor-pointer bold"
                                onClick={(e) => {
                                  cancel();
                                }}
                              >
                                cancel
                              </button>
                            </div>
                          </div>
                        </div>
                      ))
                    : null}
                </h3>
              </div>
            </div>
          ) : null}
        </div>
      ) : (
        history.push("/home")
      )}
    </div>
  );
}

export default UpdateBooking;
