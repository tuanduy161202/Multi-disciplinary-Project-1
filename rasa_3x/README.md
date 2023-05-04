# Rasa 3x

## Setup

1. Create Virtual Environment: 
```bash
python -m venv rasa_venv # Create Virtual Env
rasa_venv\Scripts\activate.bat # Activate Venv on Window
```
2. Setup Rasa
```bash
python pip install -r requirements.txt
```

## Training

1. NLU file: `data/nlu.yml`

- Define Intents with examples, Regex, ...

2. Stories: `data/stories.yml`

- Define Stories

3. Actions: `actions/actions.py`

- Define custom actions.

- Must set `action_endpoint` in `endpoints.yml`

4. Domain file: `domain.yml`

- Define entities, slots, responses, actions and intents.

## Deploy

### Chat using command prompt
1. Run Actions server:
```bash
rasa run actions
```
2. Chat with Assistant **(In another terminal)**:
```bash
rasa shell
```

### Chat using api
1. Run API server:
```bash
rasa run --enable-api
```
2. Run Actions server **(In another terminal)**:
```bash
rasa run actions
```

Optional arguments:
- `--model`: specific model_path
- `--endpoint`: specific endpoint_path