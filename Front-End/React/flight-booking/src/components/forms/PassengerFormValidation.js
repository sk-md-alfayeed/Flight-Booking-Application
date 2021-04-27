export default function validate(values) {
  let errors = {};

  if (!values.firstname) {
    errors.firstname = "Firstname is required";
  } else if (values.firstname.length < 2) {
    errors.firstname = "Firstname must be 2 or more characters";
  }
  if (!values.lastname) {
    errors.lastname = "Lastname is required";
  } else if (values.lastname.length < 2) {
    errors.lastname = "Lastname must be 2 or more characters";
  }
  if (!values.age) {
    errors.age = "Age is required";
  } else if (values.age < 5 || values > 120) {
    errors.age = "Age must be between 5 and 120";
  }
  return errors;
}
