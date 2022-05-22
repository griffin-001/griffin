import axios from "axios";
import {useEffect} from "react";

export default function useCancelAxiosOnUnmount() {
  // this is a cleanup function
  // it runs when the component unmounts
  useEffect(() => {
    const source = axios.CancelToken.source();
    return () => {
      source.cancel("Cancelling axios calls...")
    };
  }, []);

}