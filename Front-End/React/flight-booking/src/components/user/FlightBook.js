import axios from "axios";
import React, { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import FlightService from "../../services/FlightService";
import { Button } from "react-bootstrap";

import validate from "../forms/PassengerFormValidation";
import useForm from "../forms/useForm";

//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//loading Razorpay Webpage
function loadScript(src) {
  return new Promise((resolve) => {
    const script = document.createElement("script");
    script.src = src;
    script.onload = () => {
      resolve(true);
    };
    script.onerror = () => {
      resolve(false);
    };
    document.body.appendChild(script);
  });
}

const __DEV__ = document.domain === "localhost";

//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//randomId Generator
const getRandomId = (min = 0, max = 500000) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  const num = Math.floor(Math.random() * (max - min + 1)) + min;
  return num.toString().padStart(4, "0");
};
const randomNumber = getRandomId();

//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//randomId Generator

function uniqueId(stringLength, possible) {
  stringLength = stringLength || 10;
  possible = possible || "ABCDEFGHJKMNPQRSTUXY";
  var text = "";

  for (var i = 0; i < stringLength; i++) {
    var character = getCharacter(possible);
    while (text.length > 0 && character === text.substr(-1)) {
      character = getCharacter(possible);
    }
    text += character;
  }

  return text;
}

function getCharacter(possible) {
  return possible.charAt(Math.floor(Math.random() * possible.length));
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//main function
function FlightBook() {
  const { id } = useParams();
  const username = localStorage.getItem("username");
  const email = localStorage.getItem("email");
  const backEndTokenBooking = localStorage.getItem("backEndTokenBooking");

  const history = useHistory();
  const [flight, setFlight] = useState({});
  const [passengerList, setPassengerList] = useState([]);
  const [passengerId, setPassengerId] = useState(1);

  //Passenger form Validation
  const { values, errors, handleChange, handleSubmit } = useForm(
    callback,
    validate
  );

  //useEffect
  useEffect(() => {
    FlightService.getSelectedFlight(id, backEndTokenBooking)
      .then((response) => {
        if (response.data) {
          setFlight(response.data);
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));
  }, [id, backEndTokenBooking]);

  const randomPnr = uniqueId();

  // .......................................................................
  //book flight
  const bookFlight = (response) => {
    console.log(response);

    let booking = {
      id:
        flight.id +
        "-" +
        flight.airline.airlineName.trim() +
        "-" +
        randomNumber,
      pnrNo: randomPnr,
      flight: flight,
      passengerList: passengerList,
      date: new Date().toLocaleString(),
      active: true,
      email: email,
    };
    console.log(booking);

    // .......................................................................
    //Save payment details to database
    axios
      .post("http://localhost:3002/addPayment", {
        paymentid: response.razorpay_payment_id,
        orderid: response.razorpay_order_id,
        signature: response.razorpay_signature,
        bookingid: booking.id,
        totalamount: flight.fare.flightFare * passengerList.length,
        date: new Date().toLocaleString(),
        email: email,
      })
      .then((response) => {
        console.log(response);
      })
      .catch((error) => console.error(`Error :  ${error}`));

    // .......................................................................
    //Save bookig details to database
    FlightService.bookFlight(booking, backEndTokenBooking)
      .then((response) => {
        history.push(
          `/acknowledgment/${
            flight.id + "-" + flight.airline.airlineName + "-" + randomNumber
          }`
        );
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  function callback() {
    console.log("callback called");
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  //Payment Function
  async function displayRazorpay() {
    //userdata
    const info = {
      fare: flight.fare.flightFare * passengerList.length,
    };

    const res = await loadScript(
      "https://checkout.razorpay.com/v1/checkout.js"
    );

    if (!res) {
      alert("Razorpay SDK failed to load. Are you online?");
      return;
    }

    const data = await fetch("http://localhost:3002/razorpay", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(info),
    }).then((t) => t.json());

    console.log(data);

    const options = {
      key: __DEV__ ? "rzp_test_3qpMwKLiYT1YtE" : "PRODUCTION_KEY",
      currency: data.currency,
      amount: data.amount.toString(),
      order_id: data.id,
      name: flight.id,
      description: flight.airline.airlineName,
      image:
        "https://e7.pngegg.com/pngimages/953/550/png-clipart-pictogram-airplane-wikipedia-information-computer-icons-aircraft-icon-blue-logo.png",
      handler: function (response) {
        if (response.razorpay_payment_id) {
          bookFlight(response);
        }
        // alert(response.razorpay_payment_id);
        // alert(response.razorpay_order_id);
        // alert(response.razorpay_signature);
      },
      prefill: {
        name: username,
        email: "alfayeed@flights.com",
        phone_number: "9900909090",
      },
    };
    const paymentObject = new window.Razorpay(options);
    paymentObject.open();
  }

  // .......................................................................
  //Add Passenger
  const addPassenger = () => {
    const errors = validate(values);
    if (Object.keys(errors).length === 0) {
      let passenger = {
        id: passengerId,
        firstName: values.firstname,
        middleName: values.middlename,
        lastName: values.lastname,
        age: values.age,
        gender: values.gender,
        seatNo: "null",
      };
      setPassengerId(passengerId + 1);
      setPassengerList([...passengerList, passenger]);
    }
  };

  // .......................................................................
  //Delete Passenger
  const deletePassenger = (passengerId) => {
    setPassengerList(
      passengerList.filter((passenger) => passenger.id !== passengerId)
    );
  };
  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  // Handle Change
  const handleChangeInput = (id, event) => {
    const newPassengers = passengerList.map((i) => {
      if (id === i.id) {
        i[event.target.name] = event.target.value;
      }
      return i;
    });
    setPassengerList(newPassengers);
  };
  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
  return (
    <div>
      <div className="container">
        {Object.keys(flight).length !== 0 ? (
          <div className="containerCard">
            <div className="upper">
              <h2 className="text-center">
                {flight.airline.airlineName} - {flight.id}
              </h2>
              <h3>
                <div className="cardsFlight text-center">
                  <div className="col-sm">
                    {" "}
                    <span>{flight.departureTime}</span>
                    <br />
                    {flight.departureAirport.airportName}
                  </div>
                  <div className="col-sm">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                      <polygon points="16.172 9 10.101 2.929 11.515 1.515 20 10 19.293 10.707 11.515 18.485 10.101 17.071 16.172 11 0 11 0 9" />
                    </svg>
                  </div>
                  <div className="col-sm">
                    {" "}
                    <span>{flight.arrivalTime}</span>
                    <br />
                    {flight.destinationAirport.airportName}
                  </div>

                  <div className="col-sm">
                    <span>{"Departure Date"}</span>
                    <br />
                    {flight.departureDate}
                  </div>

                  <div className="col-sm">
                    <span>{"Arrival Date"}</span>
                    <br />
                    {flight.arrivalDate}
                  </div>

                  <div className="col-sm">
                    <h2>
                      {"\u20B9" + flight.fare.flightFare * passengerList.length}
                    </h2>
                  </div>
                </div>
              </h3>
            </div>
          </div>
        ) : null}
      </div>

      {passengerList.length !== 0
        ? passengerList.map((passenger, idx) => (
            <div key={idx} className="containerPassenger">
              <div className="upperPassenger">
                <p>{passenger.id}</p>
                <div className="row align-items-end">
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
                      value={passenger.middleName || " "}
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

                  <div className="col-sm">
                    <button
                      id="delete"
                      className="btn-block secondary-button button cursor-pointer bold"
                      onClick={() => deletePassenger(passenger.id)}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))
        : null}

      {passengerList.length <= 2 ? (
        <div className="containerPassenger">
          <div className="upperPassenger">
            <form
              className="row align-items-end"
              onSubmit={handleSubmit}
              noValidate
            >
              <div className="col-sm">
                <label className="label">First Name</label>

                <input
                  autoComplete="off"
                  className={`input ${errors.firstname && "is-danger"}`}
                  type="text"
                  name="firstname"
                  onChange={handleChange}
                  value={values.firstname || ""}
                  required
                />
                {errors.firstname && (
                  <p className="help is-danger">{errors.firstname}</p>
                )}
              </div>

              <div className="col-sm">
                <label className="label">Middle Name</label>
                <input
                  className={`input ${errors.middlename && "is-danger"}`}
                  type="text"
                  name="middlename"
                  onChange={handleChange}
                  value={values.middlename || ""}
                  required
                />
                {Object.keys(errors).length !== 0 && (
                  <p className="help text-white">{"."}</p>
                )}
              </div>

              <div className="col-sm">
                <label className="label">Last Name</label>
                <input
                  autoComplete="off"
                  className={`input ${errors.lastname && "is-danger"}`}
                  type="text"
                  name="lastname"
                  onChange={handleChange}
                  value={values.lastname || ""}
                  required
                />
                {errors.lastname && (
                  <p className="help is-danger">{errors.lastname}</p>
                )}
              </div>

              <div className="col-sm">
                <label className="label">Age</label>
                <input
                  autoComplete="off"
                  className={`input ${errors.age && "is-danger"}`}
                  type="number"
                  name="age"
                  onChange={handleChange}
                  value={values.age || ""}
                  required
                />
                {errors.age && <p className="help is-danger">{errors.age}</p>}
              </div>

              <div className="col-sm">
                <label className="label">Gender</label>
                <select
                  autoComplete="off"
                  className={`input ${errors.gender && "is-danger"}`}
                  name="gender"
                  onChange={handleChange}
                  value={values.gender || ""}
                  required
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
                {Object.keys(errors).length !== 0 && (
                  <p className="help text-white">{"."}</p>
                )}
              </div>
              <div className="col-sm">
                <button
                  type="submit"
                  className="btn-block secondary-button is-info button cursor-pointer bold"
                  onClick={addPassenger}
                >
                  Add
                </button>
                {Object.keys(errors).length !== 0 && (
                  <p className="help text-white">{"."}</p>
                )}
              </div>
            </form>
          </div>
        </div>
      ) : null}
      {passengerList.length >= 1 ? (
        <div className="section is-fullheight">
          <div className="container">
            <div className="column is-4 is-offset-4">
              <Button
                className="btn btn-success text-center"
                id="submit"
                onClick={displayRazorpay}
                block
              >
                Pay
              </Button>
              {/* <Button
                className="btn btn-success text-center"
                id="submit"
                onClick={bookFlight}
                block
              >
                Pay
              </Button> */}
            </div>
          </div>
        </div>
      ) : null}
    </div>
  );
}

export default FlightBook;
