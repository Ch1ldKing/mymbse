import torch
from transformers import AutoModelForSequenceClassification, AutoTokenizer
import sys

model_save_path = "F:\\MBSEtest\\mbsedemo\\mbsedemo1\\src\\main\\resources\\LLMmodel\\finbert_lora_1.pt"
loaded_model = torch.load(model_save_path)


tokenizer_name_or_path = "ProsusAI/finbert"
tokenizer = AutoTokenizer.from_pretrained(tokenizer_name_or_path)

def process_function(text1,text2,tokenizer=tokenizer):
    tokenized_examples = tokenizer(text1, text2,
                                   max_length=256,
                                   padding="max_length",
                                   truncation=True,
                                   return_tensors="pt")
    return tokenized_examples

def gen_prediction(text1,text2):
    t = process_function(text1,text2)
    outputs = loaded_model(**t)
    predictions = torch.nn.functional.softmax(outputs.logits, dim=-1)
    predicted_class = torch.argmax(predictions).item()
    return predicted_class

# def predict(text1,text2):
#     fin_class = gen_prediction(text1,text2)
#     if fin_class == 0:
# #             print("There is a traceability relationship between two requirement texts")
#             return true
#     else:
# #             print("There isn't a traceability relationship between two requirement texts")
#             return false

if __name__ == '__main__':
    text_1 = sys.argv[1]
    text_2 = sys.argv[2]

    fin_class = gen_prediction(text_1,text_2)
    if fin_class == 0:
        print(True)

    else:
        print(False)
