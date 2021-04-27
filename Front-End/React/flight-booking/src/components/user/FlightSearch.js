import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import FlightService from "../../services/FlightService";
// import iconFlight from "../../image/plane.svg";

function FlightSearch() {
  const history = useHistory();
  const [flights, setFlights] = useState([]);
  const [airportList, setAirportList] = useState([]);
  const [departureAirportCode, setDepartureAirportCode] = useState("");
  const [destinationAirportCode, setDestinationAirportCode] = useState("");
  const [departureDate, setDepartureDate] = useState("");

  // const [open, setOpen] = useState(false);

  useEffect(() => {
    //Getting Airports list
    FlightService.getAllAirports()
      .then((response) => {
        setAirportList(response.data);
      })
      .catch((error) => console.error(`Error :  ${error}`));
  }, []);

  const searchFlights = (event) => {
    event.preventDefault();
    let search = {
      departureAirport: departureAirportCode,
      destinationAirport: destinationAirportCode,
      departureDate: departureDate,
    };
    FlightService.getFlights(search)
      .then((response) => {
        setFlights(response.data);
      })
      .catch((error) => {
        console.error(`Error :  ${error} : No Flights Available"`);
        setFlights([]);
      });
  };

  const selectFlight = (id) => {
    history.push(`/booking/${id}`);
  };

  // const handleClick = () => {
  //   if (open) {
  //     setOpen(false);
  //   } else {
  //     setOpen(true);
  //   }
  // };

  return (
    <div>
      <div className="container">
        <div className="containerCardSearch">
          <div className="upperSearch">
            <p>Search Flights</p>

            <div className="row">
              <div className="col-sm">
                <label> Departure Airport </label>
                <select
                  className="form-control"
                  name="departureAirport"
                  value={departureAirportCode || ""}
                  onChange={(e) => {
                    setDepartureAirportCode(e.target.value);
                  }}
                >
                  <option value="-">-</option>
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
                  name="destinatonAirport"
                  value={destinationAirportCode || ""}
                  onChange={(e) => {
                    setDestinationAirportCode(e.target.value);
                  }}
                >
                  <option value="-">-</option>
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
                <label> Date </label>
                <input
                  type="date"
                  min={new Date().toISOString().slice(0, 10)}
                  placeholder="Date"
                  name="date"
                  className="form-control"
                  value={departureDate}
                  onChange={(e) => {
                    setDepartureDate(e.target.value);
                  }}
                />
              </div>
            </div>
            <br></br>

            <div className="col-sm">
              <button
                id="search"
                className=" btn-block secondary-button button cursor-pointer bold"
                onClick={searchFlights}
              >
                Search
              </button>
            </div>
          </div>
        </div>

        {flights.length !== 0
          ? flights.map((flight, idx) => (
              <div key={idx} className="containerCard">
                <div className="upper">
                  <h2 className="text-center">
                    {flight.airline.airlineName} - {flight.id}
                  </h2>
                  <h3>
                    <div className="cardsFlightMain text-center">
                      <div className="col-sm">
                        {" "}
                        <span>{flight.departureTime}</span>
                        <br />
                        {flight.departureAirport.airportName}
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
                        <h2>{"\u20B9" + flight.fare.flightFare}</h2>
                      </div>
                    </div>

                    <div className="col-sm">
                      <button
                        className="btn-block secondary-button button cursor-pointer bold"
                        onClick={(e) => selectFlight(flight.id)}
                      >
                        Book
                      </button>
                    </div>
                  </h3>
                </div>
              </div>
            ))
          : null}
      </div>
    </div>
  );
}

export default FlightSearch;
