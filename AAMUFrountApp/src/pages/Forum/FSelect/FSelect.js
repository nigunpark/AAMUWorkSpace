import React from 'react'
import './FSelect.scss';

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
        <></>
        // <span className="custom-dropdown">
        //     <select onChange={handleChange}>
        //         {
        //             options.map((item, index)=>(
        //                 <option value={item.value} key={index}>
        //                     {item.label}
        //                 </option>
        //             ))
        //         }
        //     </select>
        // </span>
        
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
        // <div className="select-box">
        //     <div className="select-box__current" tabindex="1" onChange={handleChange}>
        //         {
        //             console.log('handleChange : ',handleChange.value)
        //         }
        //         {
        //             // handleChange.value != null ?
        //             // <div className="select-box__value">
        //             //     <input className="select-box__input" type="radio" name="Ben"/>
        //             //         <p className='select-box__input-text' >
        //             //         선택
        //             //         </p>
        //             // </div> : 
        //             options.map((item, index)=>{

        //                 console.log(item.label);
                        
        //                 return(
        //                 <div className="select-box__value">
        //                     <input className="select-box__input" type="radio" id={index} key={index} value={item.value} name="Ben"/>
        //                     <p className='select-box__input-text' >
        //                         {
        //                             console.log('item.label',item.label)
        //                         }
        //                         {item.label}
        //                     </p>
        //                 </div>
        //                 );
        //             })
        //         }
        //         <img className="select-box__icon" src="http://cdn.onlinewebfonts.com/svg/img_295694.svg" alt="Arrow Icon" aria-hidden="true"/>
        //     </div>
            
        //     <ul className="select-box__list">
        //         {
        //             options.map((item, index)=>{
        //                 return (
        //                 <li>
        //                     <label className='select-box__option' aria-hidden="aria-hidden" for={index} value={item.value} key={index}>
        //                         {item.label}
        //                     </label>
        //                 </li>
        //                 );
        //             })
        //         }
        //     </ul>
        // </div>
        
    )
}

export default FSelect