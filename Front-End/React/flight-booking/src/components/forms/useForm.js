import { useState } from "react";

const useForm = (callback, validate) => {
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  // useEffect(() => {
  //   if (Object.keys(errors).length === 0 && isSubmitting) {
  //     console.log("callback Called");
  //     callback();
  //   }
  // }, [errors, callback, isSubmitting]);

  const handleSubmit = (event) => {
    event.preventDefault();
    setErrors(validate(values));

    if (Object.keys(validate(values)).length === 0) {
      console.log("callback Called");
      callback();
    }
  };

  const handleChange = (event) => {
    event.persist();
    setValues((values) => ({
      ...values,
      [event.target.name]: event.target.value,
    }));
    console.log(event.target.value);
  };

  return {
    handleChange,
    handleSubmit,
    values,
    errors,
  };
};

export default useForm;
