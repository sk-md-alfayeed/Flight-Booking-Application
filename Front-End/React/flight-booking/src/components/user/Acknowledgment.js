import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import FlightService from "../../services/FlightService";

function Acknowledgment() {
  const { id } = useParams();
  const backEndTokenBooking = localStorage.getItem("backEndTokenBooking");
  const [booking, setBooking] = useState({});
  const [payment, setPayment] = useState({});

  useEffect(() => {
    FlightService.getBooking(id, backEndTokenBooking)
      .then((response) => {
        setBooking(response.data);
      })
      .catch((error) => console.error(`Error :  ${error}`));
    axios
      .post("http://localhost:3002/getPayment", {
        bookingid: id,
      })
      .then((response) => {
        console.log(response.data);
        setPayment(response.data);
      });
  }, [id, backEndTokenBooking]);

  return (
    <div>
      <div className="container">
        <div className="containerAck">
          <div className="upperAck">
            <h4>Thank You For the Booking</h4>
            <br />

            <form className="row align-items-start">
              <div className="col-sm">
                <label> Booking Id </label>
                <p>{id}</p>
              </div>
              <div className="col-sm">
                <label> PNR No </label>
                <p>{booking.pnrNo}</p>
              </div>

              <div className="col-sm">
                <label> Payment Id </label>
                <p>{payment.paymentid}</p>
              </div>

              {/* <div className="col-sm">
                <label> Signature </label>
                <p>{payment.signature}</p>
              </div> */}

              <div className="col-sm">
                <label> Amount Paid</label>
                <p>{payment.totalamount}</p>
              </div>

              <div className="col-sm">
                <label> Date </label>
                <p>{new Date().toLocaleString()}</p>
              </div>

              <div className="col-sm">
                <label> Status </label>
                <p>{booking.active === true ? "Booked" : "Not Booked"}</p>
              </div>
            </form>
            {/* <Button className="btn btn-success">Invoice</Button> */}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Acknowledgment;
