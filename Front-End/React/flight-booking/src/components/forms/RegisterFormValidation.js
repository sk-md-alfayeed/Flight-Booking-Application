export default function validate(values) {
  let errors = {};

  if (!values.fullname) {
    errors.fullname = "Fullname is required";
  } else if (values.fullname.length < 2) {
    errors.fullname = "Fullname must be 2 or more characters";
  }

  if (!values.username) {
    errors.username = "Username is required";
  } else if (values.username.length < 4) {
    errors.username = "Username must be 4 or more characters";
  }

  if (!values.email) {
    errors.email = "Email address is required";
  } else if (!/\S+@\S+\.\S+/.test(values.email)) {
    errors.email = "Email address is invalid";
  }

  if (!values.password) {
    errors.password = "Password is required";
  } else if (values.password.length < 8) {
    errors.password = "Password must be 8 or more characters";
  }
  return errors;
}
