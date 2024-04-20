import { useNavigation } from "react-router-dom";

type Props = {
    formBtn: boolean;
}

const SubmitBtn = ({ formBtn }: Props) => {
    const navigation = useNavigation();
    const isSubmitting = navigation.state === "submitting";

    return (
        <button
            type="submit"
            className={`btn btn-block ${formBtn && 'form-btn'} `}
            disabled={isSubmitting}
        >
            {isSubmitting ? "submitting" : "submit"}
        </button>
    );
};

export default SubmitBtn;
