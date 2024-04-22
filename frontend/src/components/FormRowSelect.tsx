import {  ChangeEvent } from "react";

type Props = {
    labelText?: string;
    name: string;
    defaultValue: string;
    list: string[];
    onChange?: (e: ChangeEvent<HTMLSelectElement>) => void
}

const FormRowSelect = ({name, labelText, list, defaultValue = '', onChange}: Props) => {
    return (
        <div className="form-row">
            <label htmlFor={name} className="form-label">
                {labelText || name}
            </label>
            <select
                name={name}
                id={name}
                className="form-select"
                defaultValue={defaultValue}
                onChange={onChange}
            >
                {list.map((itemValue) => {
                    return (
                        <option value={itemValue} key={itemValue}>
                            {itemValue}
                        </option>
                    );
                })}
            </select>
        </div>
    );
};

export default FormRowSelect;
