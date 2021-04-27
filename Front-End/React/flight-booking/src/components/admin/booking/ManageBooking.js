import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import FlightService from "../../../services/FlightService";

function ManageBooking() {
  const email = localStorage.getItem("email");
  const backEndTokenBooking = localStorage.getItem("backEndTokenBooking");
  const history = useHistory();

  const [bookingList, setBookingList] = useState([]);

  useEffect(() => {
    FlightService.getAllBookings(backEndTokenBooking)
      .then((response) => {
        if (response.data !== null) {
          setBookingList(response.data);
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));
  }, [email, backEndTokenBooking]);

  const updateBooking = (id) => {
    history.push(`/update_booking/${id}`);
  };

  const cancelBooking = (booking) => {
    booking.active = !booking.active;
    FlightService.cancelBooking(booking, backEndTokenBooking)
      .then((res) => {
        history.push("/manage_booking");
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  // const deleteBooking = (bookingId) => {
  //   FlightService.deleteBooking(bookingId, backEndTokenBooking)
  //     .then(() => {
  //       history.push("/manage_booking");
  //     })
  //     .catch((error) => console.error(`Error :  ${error}`));
  // };

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  return (
    <div>
      <div className="container">
        <h1 className="text-center"> Bookings List</h1>
        {bookingList.length !== 0 ? (
          bookingList.map((booking, idx) => (
            <div key={idx} className="containerBM">
              <div className="upperBM">
                <h2 className="text-center">{booking.id}</h2>
                <br></br>
                <h3>
                  <div className="row align-items-start text-center">
                    <div className="col-sm">
                      {" "}
                      <span>{"PNR No"}</span>
                      <br />
                      {booking.pnrNo}
                    </div>
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
                      <h2>
                        {"\u20B9" +
                          booking.flight.fare.flightFare *
                            booking.passengerList.length}
                      </h2>
                    </div>
                  </div>
                  <br></br>
                  {booking.passengerList.length !== 0
                    ? booking.passengerList.map((passenger, idx) => (
                        <div key={idx} className="containerPassenger">
                          <div className="upperPassenger">
                            <h6>{passenger.id}</h6>
                            <div className="row align-items-start">
                              <div className="col-sm">
                                <label> First Name </label>
                                <p>{passenger.firstName || ""}</p>
                              </div>

                              <div className="col-sm">
                                <label> Middle Name </label>
                                <p>{passenger.middleName || ""}</p>
                              </div>

                              <div className="col-sm">
                                <label> Last Name </label>
                                <p>{passenger.lastName || ""}</p>
                              </div>

                              <div className="col-sm">
                                <label> Age </label>
                                <p>{passenger.age || ""}</p>
                              </div>

                              <div className="col-sm">
                                <label>Gender</label>
                                <p>{passenger.gender || ""}</p>
                              </div>
                            </div>
                            <div className="col-sm">
                              <button
                                id="update"
                                className="btn-block secondary-button button cursor-pointer bold"
                                onClick={() => updateBooking(booking.id)}
                              >
                                Update
                              </button>
                            </div>
                            <br></br>
                            {booking.active !== false ? (
                              <div className="col-sm">
                                <button
                                  id="cancel"
                                  className="btn-block secondary-button button cursor-pointer bold"
                                  onClick={() => cancelBooking(booking)}
                                >
                                  Cancel
                                </button>
                              </div>
                            ) : (
                              <div className="col-sm">
                                <button
                                  id="danger"
                                  className="btn-block secondary-button button cursor-pointer bold"
                                  onClick={() => cancelBooking(booking)}
                                >
                                  Canceled
                                </button>
                              </div>
                            )}
                          </div>
                        </div>
                      ))
                    : null}
                </h3>
              </div>
            </div>
          ))
        ) : (
          <div className="text-center">
            <h5>No Bookings Available</h5>
          </div>
        )}
      </div>
    </div>
  );
}

export default ManageBooking;
