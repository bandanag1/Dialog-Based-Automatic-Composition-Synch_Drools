[keyword][]Introduce fact "{name}" with property "{prop}" as list=declare {name}  {prop}: java.util.List keywords: String end
[keyword][]Introduce fact "{value}"=declare {value} keywords: String end
[keyword][]Set keywords of fact "{fact}" to "{keywords}"=rule "{fact}Keywords"	when f: {fact}() then f.setKeywords("{keywords}"); end
[condition][]There is a {value} named {name}={name}: {value}()
[condition][]There is a {value}=fact: {value}()
[condition][]There is no {value}=not {value}()
[keyword][]above fact=fact
[keyword][]user current location=userModel.getCurrentLocation()
[keyword][]user current city=userModel.getCurrentLocation().getCity()
[condition][]Instance is at least {number} and field is "{value}"=i: Instance(number > {number}, location=="{value}")
[consequence][]Log : "{message}"=System.out.println("{message}");
[consequence][]Set field of instance to "{value}"=i.setField("{value}");
[consequence][]Create instance : "{value}"=insert(new Instance("{value}"));
[condition][]There is no current Instance with field : "{value}"=not Instance(field == "{value}")
[consequence][]Report error : "{error}"=System.err.println("{error}");
[consequence][]Retract the fact : '{variable}'=retract({variable}); //this would retract bound variable {variable}
[consequence][]Ask Yes-No Question: "{question}" with {parameter}=Boolean answer = Communicator.askYesNoQuestion(String.format("{question}",{parameter}));
[consequence][]Ask Yes-No Question: "{question}"=Boolean answer = Communicator.askYesNoQuestion("{question}");
[consequence][]Ask Question: "{question}" with {parameter}=String answer = Communicator.askGeneralQuestion(String.format("{question}",{parameter}));
[consequence][]Ask Question: "{question}"=String answer = Communicator.askGeneralQuestion(String.format("{question}"));
[consequence][]Ask For Location: "{question}"=String[] answer = Communicator.askGeneralQuestion("{question}").split("/");
[consequence][]Ask Question with Multiple Choice: "{question}"=List<String> answer = Communicator.askMultipleChoiceQuestion("{question}");
[consequence][]Answer is yes=answer==true
[consequence][]Answer is no=answer==false
[consequence][]Answer contains {value}=answer.contains({value})
[consequence][]Answer is "{value}"=answer.equals("{value}")
[consequence][]Remember fact: {value}=insert(new {value});
[consequence][]Remove {value}=retract({value});
[consequence][]Copy fact from: {value}=insert({value});
[condition][]User speaks language of {value}=eval(!userModel.speaksLanguageOf({value}))
[condition][]User is not in the same city as {value}=eval(!userModel.isInTheSameCityAs({value}))
[consequence][]Put answer to {value} list of fact=fact.set{value}(new LinkedList<Object>());fact.get{value}().addAll(answer);
[consequence][]Print Result : "{value}"=System.out.println("{value}");
[consequence][]Ask For Topics: "{question}"=String[] answer = Communicator.askGeneralQuestion("{question}").split(",");
[condition][]One topic entered of {value}=eval(userModel.isNumberOfTopicsOne({value}))
[condition][]Two topics entered of {value}=eval(userModel.isNumberOfTopicsTwo({value}))
[condition][]Three topics entered of {value}=eval(userModel.isNumberOfTopicsThree({value}))
