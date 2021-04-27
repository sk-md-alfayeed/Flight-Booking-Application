import React, { useState, useEffect } from "react";
import useForm from "../../forms/useForm";
import { Link, useHistory } from "react-router-dom";
import FlightService from "../../../services/FlightService";
import validate from "../../forms/FlightValidation";

function ManageFlight() {
  const backEndTokenFlight = localStorage.getItem("backEndTokenFlight");
  const history = useHistory();
  const { values, errors, handleChange, handleSubmit } = useForm(
    callback,
    validate
  );

  const [flights, setFlights] = useState([]);

  const [airportList, setAirportList] = useState([]);
  const [airlineList, setAirlineList] = useState([]);

  const [addClicked, setAddClicked] = useState(false);

  //randomId Generator
  const getRandomId = (min = 0, max = 5000) => {
    min = Math.ceil(min);
    max = Math.floor(max);
    const num = Math.floor(Math.random() * (max - min + 1)) + min;
    return num.toString().padStart(4, "0");
  };

  useEffect(() => {
    let unmounted = false;

    FlightService.getAllAirlines()
      .then((response) => {
        if (response.data !== null) {
          if (!unmounted) {
            setAirlineList(response.data);
          }
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));
    FlightService.getAllAirports()
      .then((response) => {
        if (response.data !== null) {
          if (!unmounted) {
            setAirportList(response.data);
          }
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));

    FlightService.getAllFlights()
      .then((response) => {
        if (response.data !== null) {
          if (!unmounted) {
            setFlights(response.data);
          }
        }
      })
      .catch((error) => console.error(`Error :  ${error}`));
    return () => {
      unmounted = true;
    };
  }, []);

  function callback() {}

  const createFlight = () => {
    if (Object.keys(errors).length === 0) {
      let newFlight = {
        id: values.airlineNo + "-" + getRandomId(),
        airline:
          values.airlineNo !== ""
            ? airlineList.find(
                (element) => element.airlineNo === values.airlineNo
              )
            : {},
        departureAirport:
          values.departureAirportCode !== ""
            ? airportList.find(
                (element) => element.airportCode === values.departureAirportCode
              )
            : {},
        destinationAirport:
          values.destinationAirportCode !== ""
            ? airportList.find(
                (element) =>
                  element.airportCode === values.destinationAirportCode
              )
            : {},
        departureDate: values.departureDate,
        arrivalDate: values.arrivalDate,
        departureTime: values.departureTime,
        arrivalTime: values.arrivalTime,
        seats: values.seats,
      };
      addFlight(newFlight);
    }
  };

  const addFlight = (flight) => {
    FlightService.addFlight(flight, backEndTokenFlight)
      .then((res) => {
        setFlights([...flights, flight]);
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  const add = () => {
    setAddClicked(true);
  };

  const cancel = () => {
    setAddClicked(false);
  };

  const deleteFlight = (flightId) => {
    FlightService.deleteFlight(flightId, backEndTokenFlight)
      .then((res) => {
        setFlights(flights.filter((flight) => flight.id !== flightId));
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  //update
  const updateFlight = (flightId) => {
    const updatedFlight =
      flightId !== "" ? flights.find((element) => element.id === flightId) : {};
    console.log(updatedFlight);
    FlightService.updateFlight(updatedFlight, backEndTokenFlight)
      .then((res) => {
        history.push("/manage_flight");
      })
      .catch((error) => console.error(`Error :  ${error}`));
  };

  const handleChangeInput = (id, event) => {
    const newFlights = flights.map((i) => {
      if (id === i.id) {
        if (event.target.name === "airlineNo") {
          i["airline"] =
            event.target.value !== ""
              ? airlineList.find(
                  (element) => element.airlineNo === event.target.value
                )
              : {};

          console.log(i["airline"]);
          return i;
        }
        if (event.target.name === "departureAirport") {
          i["departureAirport"] =
            event.target.value !== ""
              ? airportList.find(
                  (element) => element.airportCode === event.target.value
                )
              : {};
          console.log(i[event.target.name]);
          return i;
        }
        if (event.target.name === "destinationAirport") {
          i["destinationAirport"] =
            event.target.value !== ""
              ? airportList.find(
                  (element) => element.airportCode === event.target.value
                )
              : {};
          console.log(i[event.target.name]);
          return i;
        } else {
          i[event.target.name] = event.target.value;
        }
      }
      return i;
    });
    setFlights(newFlights);
  };

  return (
    <div>
      <div className="container">
        <div className="containerAir">
          <div className="upperAir">
            <div className="row text-center">
              <Link className="btn col-sm" to={"/add_or_update_airport/add"}>
                Add Airport
              </Link>
              <button className="btn col-sm" onClick={add}>
                {" "}
                Add
              </button>
              <Link className="btn col-sm" to={"/add_or_update_airline/add"}>
                Add Airline
              </Link>
            </div>
          </div>
        </div>
      </div>

      <h1 className="text-center"> Flights List</h1>

      <br></br>
      {addClicked === true ? (
        <div>
          <div className="containerAF">
            <div className="upperAF">
              <form
                className="cards align-items-start"
                onSubmit={handleSubmit}
                noValidate
              >
                <div className="col-sm">
                  <label> Airline Name </label>
                  <select
                    autoComplete="off"
                    className={`input ${errors.airlineNo && "is-danger"}`}
                    name="airlineNo"
                    onChange={handleChange}
                    value={values.airlineNo || ""}
                    required
                  >
                    <option value="">-</option>
                    {airlineList.map((airline) => (
                      <option key={airline.airlineNo} value={airline.airlineNo}>
                        {`${airline.airlineNo} (${airline.airlineName})`}
                      </option>
                    ))}
                  </select>
                  {errors.airlineNo && (
                    <p className="help is-danger">{errors.airlineNo}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Departure Airport </label>
                  <select
                    autoComplete="off"
                    className={`input ${
                      errors.departureAirportCode && "is-danger"
                    }`}
                    name="departureAirportCode"
                    onChange={handleChange}
                    value={values.departureAirportCode || ""}
                    required
                  >
                    <option value="">-</option>
                    {airportList.map((airport) => (
                      <option
                        key={airport.airportCode}
                        value={airport.airportCode}
                      >
                        {`${airport.airportName} (${airport.airportCode})`}
                      </option>
                    ))}
                  </select>
                  {errors.departureAirportCode && (
                    <p className="help is-danger">
                      {errors.departureAirportCode}
                    </p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Destination Airport </label>
                  <select
                    autoComplete="off"
                    className={`input ${
                      errors.destinationAirportCode && "is-danger"
                    }`}
                    name="destinationAirportCode"
                    onChange={handleChange}
                    value={values.destinationAirportCode || ""}
                    required
                  >
                    <option value="">-</option>
                    {airportList.map((airport) => (
                      <option
                        key={airport.airportCode}
                        value={airport.airportCode}
                      >
                        {`${airport.airportName} (${airport.airportCode})`}
                      </option>
                    ))}
                  </select>
                  {errors.destinationAirportCode && (
                    <p className="help is-danger">
                      {errors.destinationAirportCode}
                    </p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Departure Date </label>
                  <input
                    autoComplete="off"
                    className={`input ${errors.departureDate && "is-danger"}`}
                    type="date"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Departure Date"
                    name="departureDate"
                    onChange={handleChange}
                    value={values.departureDate || ""}
                    required
                  />
                  {errors.departureDate && (
                    <p className="help is-danger">{errors.departureDate}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Arrival Date </label>
                  <input
                    autoComplete="off"
                    className={`input ${errors.arrivalDate && "is-danger"}`}
                    type="date"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Arrival Date"
                    name="arrivalDate"
                    onChange={handleChange}
                    value={values.arrivalDate || ""}
                    required
                  />
                  {errors.arrivalDate && (
                    <p className="help is-danger">{errors.arrivalDate}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Departure Time </label>
                  <input
                    autoComplete="off"
                    className={`input ${errors.departureTime && "is-danger"}`}
                    type="time"
                    placeholder="Departure Time"
                    name="departureTime"
                    onChange={handleChange}
                    value={values.departureTime || ""}
                    required
                  />
                  {errors.departureTime && (
                    <p className="help is-danger">{errors.departureTime}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Arrival Time </label>
                  <input
                    autoComplete="off"
                    className={`input ${errors.arrivalTime && "is-danger"}`}
                    type="time"
                    placeholder="Arrival Time"
                    name="arrivalTime"
                    onChange={handleChange}
                    value={values.arrivalTime || ""}
                    required
                  />
                  {errors.arrivalTime && (
                    <p className="help is-danger">{errors.arrivalTime}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label> Seats </label>
                  <input
                    autoComplete="off"
                    className={`input ${errors.seats && "is-danger"}`}
                    type="text"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Seats"
                    name="seats"
                    onChange={handleChange}
                    value={values.seats || ""}
                    required
                  />
                  {errors.seats && (
                    <p className="help is-danger">{errors.seats}</p>
                  )}
                </div>

                <div className="col-sm">
                  <label className="text-white"> Create Flight </label>
                  <button
                    type="submit"
                    id="add"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={createFlight}
                  >
                    Add
                  </button>
                </div>

                <div className="col-sm">
                  <label className="text-white"> cancel </label>
                  <button
                    id="cancel"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={cancel}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      ) : null}
      <br></br>
      {flights.length !== 0 ? (
        flights.map((flight, idx) => (
          <div key={idx} className="containerFM">
            <div className="upperFM">
              <p>{flight.id}</p>
              <div className="cards align-items-end">
                <div className="col-sm">
                  <label> Airline Name </label>
                  <select
                    className="form-control"
                    name="airlineNo"
                    value={flight.airline.airlineNo || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  >
                    <option value="">-</option>
                    {airlineList.map((airline) => (
                      <option key={airline.airlineNo} value={airline.airlineNo}>
                        {`${airline.airlineNo} (${airline.airlineName})`}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="col-sm">
                  <label> Departure Airport </label>
                  <select
                    className="form-control"
                    name="departureAirport"
                    value={flight.departureAirport.airportCode || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  >
                    <option value="">-</option>
                    {airportList.map((airport) => (
                      <option
                        key={airport.airportCode}
                        value={airport.airportCode}
                      >
                        {`${airport.airportName} (${airport.airportCode})`}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="col-sm">
                  <label> Destination Airport </label>
                  <select
                    className="form-control"
                    name="destinationAirport"
                    value={flight.destinationAirport.airportCode || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  >
                    <option value="">-</option>
                    {airportList.map((airport) => (
                      <option
                        key={airport.airportCode}
                        value={airport.airportCode}
                      >
                        {`${airport.airportName} (${airport.airportCode})`}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="col-sm">
                  <label> Departure Date </label>
                  <input
                    type="date"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Departure Date"
                    name="departureDate"
                    className="form-control"
                    value={flight.departureDate || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  />
                </div>

                <div className="col-sm">
                  <label> Arrival Date </label>
                  <input
                    type="date"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Arrival Date"
                    name="arrivalDate"
                    className="form-control"
                    value={flight.arrivalDate || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  />
                </div>

                <div className="col-sm">
                  <label> Departure Time </label>
                  <input
                    type="time"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Departure Time"
                    name="departureTime"
                    className="form-control"
                    value={flight.departureTime || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  />
                </div>

                <div className="col-sm">
                  <label> Arrival Time </label>
                  <input
                    type="time"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Arrival Time"
                    name="arrivalTime"
                    className="form-control"
                    value={flight.arrivalTime || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  />
                </div>

                <div className="col-sm">
                  <label> Seats </label>
                  <input
                    type="text"
                    min={new Date().toISOString().slice(0, 10)}
                    placeholder="Seats"
                    name="seats"
                    className="form-control"
                    value={flight.seats || ""}
                    onChange={(e) => {
                      handleChangeInput(flight.id, e);
                    }}
                  />
                </div>

                <div className="col-sm">
                  <button
                    id="update"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={(e) => {
                      updateFlight(flight.id);
                    }}
                  >
                    Update
                  </button>
                </div>

                <div className="col-sm">
                  <button
                    id="delete"
                    className="btn-block secondary-button button cursor-pointer bold"
                    onClick={() => deleteFlight(flight.id)}
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
          <h5>No Flight Available</h5>
        </div>
      )}
    </div>
  );
}

export default ManageFlight;
