import React from 'react'

const FSelect = () => {

    const handleChange = (e) => {
        const value = e.target.value;
        
        console.log(value);
    }
    
    const options = [
        { value: 'defalut', label: 'None' },
        { value: 'title', label: '제목' },
        { value: 'content', label: '내용' },
        { value: 'tag', label: '태그' }
      ]      

    return (
        <>
            <select className='FSelect_bar' onChange={handleChange}>
                {
                    options.map((item, index)=>(
                        <option value={item.value} key={index}>
                            {item.label}
                        </option>
                    ))
                }
            </select>
        </>
    )
}

export default FSelect