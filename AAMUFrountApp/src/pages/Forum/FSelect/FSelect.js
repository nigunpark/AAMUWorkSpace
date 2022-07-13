import React from 'react'

const FSelect = () => {

    const handleChange = (e) => {
        const value = e.target.value;
        
        console.log(value);
    }
    
    const options = [
        { value: 'defalut', label: '선택' },
        { value: 'title', label: '제목' },
        { value: 'content', label: '내용' },
        { value: 'tag', label: '태그' }
      ]      

    return (
        // <>
        //     <select className='FSelect_bar' onChange={handleChange}>
        //         {
        //             options.map((item, index)=>(
        //                 <option className='Fselect_option' value={item.value} key={index}>
        //                     {item.label}
        //                 </option>
        //             ))
        //         }
        //     </select>

        // </>
        <div className="select-box">
            <div className="select-box__current" tabindex="1" onChange={handleChange}>
                {/* <div className="select-box__value">
                    <input className="select-box__input" type="radio" id="0" value="1" name="Ben" />  checked="checked" 
                    <p className="select-box__input-text">선택</p>
                </div>
                <div className="select-box__value"><input className="select-box__input" type="radio" id="1" value="2" name="Ben" />
                    <p className="select-box__input-text">제목</p>
                </div>
                <div className="select-box__value"><input className="select-box__input" type="radio" id="2" value="3" name="Ben" />
                    <p className="select-box__input-text">내용</p>
                </div>
                <div className="select-box__value"><input className="select-box__input" type="radio" id="3" value="4" name="Ben" />
                    <p className="select-box__input-text">태그</p>
                </div> */}
                {
                    options.map((item, index)=>{
                        console.log(item.label);
                        return(
                        <div className="select-box__value">
                            <input className="select-box__input" type="radio" id={index} key={index} value={item.value} name="Ben" />
                            <p className='select-box__input-text'>
                                {item.label}
                            </p>
                        </div>
                        );
                    })
                }
                <img className="select-box__icon" src="http://cdn.onlinewebfonts.com/svg/img_295694.svg" alt="Arrow Icon" aria-hidden="true" />
            </div>
            <ul className="select-box__list">
                {
                    options.map((item, index)=>{
                        return (
                        <li>
                            <label className='select-box__option' aria-hidden="aria-hidden" for={index} value={item.value} key={index}>
                                {item.label}
                            </label>
                        </li>
                        );
                    })
                }
                
                {/* <li><label className="select-box__option" for="0" aria-hidden="aria-hidden">Cream</label></li>
                <li><label className="select-box__option" for="1" aria-hidden="aria-hidden">Cheese</label></li>
                <li><label className="select-box__option" for="2" aria-hidden="aria-hidden">Milk</label></li>
                <li><label className="select-box__option" for="3" aria-hidden="aria-hidden">Honey</label></li>
                <li><label className="select-box__option" for="4" aria-hidden="aria-hidden">Toast</label></li> */}
            </ul>
            
        </div>
    )
}

export default FSelect